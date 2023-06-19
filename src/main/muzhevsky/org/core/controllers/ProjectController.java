package muzhevsky.org.core.controllers;

import lombok.SneakyThrows;
import muzhevsky.org.auth.dtos.AccountDto;
import muzhevsky.org.auth.dtos.AccountIdentityDto;
import muzhevsky.org.auth.enums.Role;
import muzhevsky.org.auth.exceptions.TokenExpiredException;
import muzhevsky.org.auth.services.AuthorizationService;
import muzhevsky.org.core.dtos.CompanyDto;
import muzhevsky.org.core.dtos.ProjectCreationForm;
import muzhevsky.org.core.entities.Project;
import muzhevsky.org.core.entities.ProjectAndCompany;
import muzhevsky.org.core.enums.EstimateStatus;
import muzhevsky.org.core.enums.ProjectStatus;
import muzhevsky.org.core.repos.ProjectRepository;
import muzhevsky.org.core.repos.ProjectsAndCompaniesRepository;
import muzhevsky.org.core.services.FetchEstimatesService;
import muzhevsky.org.files.repos.ImageFileRepository;
import org.keycloak.common.VerificationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.view.RedirectView;

import javax.ws.rs.NotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Controller
public class ProjectController {
    @Autowired
    ProjectRepository projectRepository;
    @Autowired
    ImageFileRepository imageFileRepository;
    @Autowired
    AuthorizationService authorizationService;
    @Autowired
    ProjectsAndCompaniesRepository projectsAndCompaniesRepository;
    @Autowired
    FetchEstimatesService fetchEstimatesService;

    @GetMapping("/")
    public RedirectView rootPage(){
        return new RedirectView("/projects");
    }

    @GetMapping("/projects")
    @SneakyThrows
    public String getAllProjects(@Nullable @RequestAttribute("accessToken") String accessToken, Model model){
        var projects = projectRepository.findAllByStatus(ProjectStatus.ACTIVE.get());
        List<String> roles = new ArrayList<>();
        if (accessToken != null)
            roles = authorizationService.getRoles(accessToken);

        model.addAttribute("projects", projects);
        model.addAttribute("roles", roles);
        return "projects/projects";
    }


    @GetMapping("/project/{id}")
    public String projectPage(@Nullable @RequestAttribute("accessToken") String accessToken, Model model, @PathVariable int id) {
        model.addAttribute("roles", new ArrayList<String>());
        List<String> roles = new ArrayList<String>();
        Project project = null;
        boolean isOwner = false;
        boolean isImplementor = false;
        AccountDto ownerDto = null;
        AccountIdentityDto userIdentity = null;
        try{
            roles = authorizationService.getRoles(accessToken);
            userIdentity = authorizationService.getUserIdentity(accessToken);
            ownerDto = authorizationService.getAccountDtoById(userIdentity.getId());
            var optional = projectRepository.findById(id);
            if (optional.isEmpty())
                return "redirect:errors/notfound";
            project = optional.get();

            isOwner = userIdentity.getId().equals(project.getOwnerId());
            isImplementor = !projectsAndCompaniesRepository.findProjectAndCompaniesByProjectIdAndCompanyId(id, userIdentity.getId()).isEmpty();
        }
        catch (VerificationException | TokenExpiredException ex){
            var optional = projectRepository.findById(id);
            if (optional.isEmpty())
                return "redirect:errors/notfound";

            project = optional.get();
        }

        if (!isOwner && !project.getStatus().equals(ProjectStatus.ACTIVE.get()) && !roles.contains(Role.ADMIN.get()))
            return "redirect:/myprojects";

        System.out.println(ownerDto);

        model.addAttribute("visiterId", userIdentity != null ? userIdentity.getId() : "");
        model.addAttribute("ownerData", ownerDto);
        model.addAttribute("roles", roles);
        model.addAttribute("project", project);
        model.addAttribute("isOwner", isOwner);
        model.addAttribute("isImplementor", isImplementor);
        return "projects/project";
    }

    @SneakyThrows
    @PostMapping("/acceptproject/{id}")
    public RedirectView acceptProject(@RequestAttribute("accessToken") String accessToken, @PathVariable int id){
        projectsAndCompaniesRepository.createProjectAndCompanyRecord(id, authorizationService.getUserIdentity(accessToken).getId());
        return new RedirectView("/myprojects");
    }

    @GetMapping("/myprojects")
    @SneakyThrows
    public String getMyProjects(@RequestAttribute("accessToken") String accessToken, Model model) {
        var roles = authorizationService.getRoles(accessToken);
        var user = authorizationService.getUserIdentity(accessToken);

        List<Project> projects = List.of();
        if (roles.contains(Role.USER.get()))
            projects = projectRepository.findAllByOwnerId(user.getId());
        if (roles.contains(Role.COMPANY.get())){
            var projectsAndCompanies = projectsAndCompaniesRepository.findProjectAndCompaniesByCompanyId(user.getId());
            projects = (List<Project>) projectRepository.findAllById(projectsAndCompanies.stream()
                    .map(ProjectAndCompany::getProjectId)
                    .collect(Collectors.toList()));
            projects = projects.stream().filter(project -> project.getStatus().equals(ProjectStatus.ACTIVE.get())).toList();
        }
        if (roles.contains(Role.ADMIN.get())){
            projects = projectRepository.findAllByStatus(ProjectStatus.ONVERIFICATION.get());
        }

        model.addAttribute("roles", roles);
        model.addAttribute("projects", projects);
        return "projects/myProjects";
    }

    @GetMapping("/myprojects/create")
    public String createProjectPage() {
        return "projects/createProject";
    }

    @PostMapping("/myprojects/create")
    @SneakyThrows
    public RedirectView createProject(@RequestAttribute("accessToken") String accessToken, @ModelAttribute ProjectCreationForm form) {
        var project = new Project(form);
        var image = form.getImage();
        var imageName = imageFileRepository.save(image.getBytes(), image.getOriginalFilename());
        var user = authorizationService.getUserIdentity(accessToken);

        project.setImageFileName(imageName);
        project.setOwnerId(user.getId());
        project.setStatus(ProjectStatus.ONVERIFICATION.get());
        projectRepository.save(project);
        return new RedirectView("/myprojects");
    }

    @GetMapping("/project/{id}/companies")
    public String projectCompanies(@PathVariable int id, Model model)
    {
        try{
            var companyIds = projectsAndCompaniesRepository.findProjectAndCompaniesByProjectId(id)
                    .stream()
                    .map(ProjectAndCompany::getCompanyId)
                    .toList();
            var project = projectRepository.findById(id).orElseThrow(NotFoundException::new);

            if (project.getStatus().equals("on verification"))
                return "redirect:/project/"+id;

            var companyDtos = new ArrayList<CompanyDto>();
            for (var companyId : companyIds){
                var estimates = fetchEstimatesService.getEstimatesSimpleDataByProjectAndCompany(id, companyId);
                var estimateStatus = "сметы отсутствуют";
                if (estimates.stream().anyMatch(estimateSimpleDto -> estimateSimpleDto.getStatus().equals(EstimateStatus.ACCEPTED.get())))
                    estimateStatus = "есть принятая смета";
                else if (estimates.stream().anyMatch(estimateSimpleDto -> estimateSimpleDto.getStatus().equals(EstimateStatus.ONUSERCONSIDERMENT.get())))
                    estimateStatus = "есть неоцененная смета";

                var company = authorizationService.getAccountDtoById(companyId);
                var companyDto = new CompanyDto(companyId, company.getAttributes().get("companyName").get(0),
                        estimateStatus);
                companyDtos.add(companyDto);
            }

            model.addAttribute("companies", companyDtos);
            model.addAttribute("project", project);
            return "projects/companies";
        }
        catch (NotFoundException notFoundException){
            return "errors/notfound";
        }
    }

    @PostMapping("/project/{projectId}/decline")
    public String declineProject(@PathVariable int projectId){
        projectRepository.findById(projectId).orElseThrow(NotFoundException::new);

        projectRepository.updateStatus(ProjectStatus.DECLINEDBYMODERATION.get(), projectId);
        return "redirect:/myprojects";
    }

    @PostMapping("/project/{projectId}/confirm")
    public String acceptProject(@PathVariable int projectId){
        projectRepository.findById(projectId).orElseThrow(NotFoundException::new);

        projectRepository.updateStatus(ProjectStatus.ACTIVE.get(), projectId);
        return "redirect:/myprojects";
    }

    @PostMapping("/project/{projectId}/cancel")
    public String cancelProject(@PathVariable int projectId){
        projectRepository.findById(projectId).orElseThrow(NotFoundException::new);

        projectRepository.updateStatus(ProjectStatus.CANCELED.get(), projectId);
        return "redirect:/myprojects";
    }
}