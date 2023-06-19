package muzhevsky.org.core.controllers;

import lombok.SneakyThrows;
import muzhevsky.org.auth.enums.Role;
import muzhevsky.org.auth.services.AuthorizationService;
import muzhevsky.org.core.dtos.EstimateRequestDto;
import muzhevsky.org.core.repos.ProjectRepository;
import muzhevsky.org.core.repos.ProjectsAndCompaniesRepository;
import muzhevsky.org.core.services.ManageEstimateService;
import muzhevsky.org.core.services.FetchEstimatesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.view.RedirectView;

import javax.ws.rs.NotFoundException;

@Controller
public class EstimatesController {
    @Autowired
    ProjectRepository projectRepository;
    @Autowired
    ProjectsAndCompaniesRepository projectsAndCompaniesRepository;
    @Autowired
    AuthorizationService authorizationService;
    @Autowired
    FetchEstimatesService fetchEstimatesService;
    @Autowired
    ManageEstimateService manageEstimateService;

    @GetMapping("/project/{projectId}/estimates/{companyId}")
    @SneakyThrows
    public String getEstimatesByCompanyId(@RequestAttribute String accessToken,
                                          @PathVariable int projectId,
                                          @PathVariable String companyId,
                                          Model model){
        var roles = authorizationService.getRoles(accessToken);

        if (roles.contains(Role.USER.get()))
            if (projectRepository.findById(projectId).isEmpty())
                return "redirect:/myprojects";
        if (roles.contains(Role.COMPANY.get()))
            if (projectsAndCompaniesRepository.findProjectAndCompaniesByProjectIdAndCompanyId(projectId, companyId).isEmpty())
                return "redirect:/myprojects";

        var estimates = fetchEstimatesService.getEstimatesSimpleDataByProjectAndCompany(projectId, companyId);

        model.addAttribute("estimates", estimates);
        model.addAttribute("isImplementor", roles.contains(Role.COMPANY.get()));
        model.addAttribute("projectId", projectId);
        model.addAttribute("companyName",
                authorizationService.getAccountDtoById(companyId).getAttributes().get("companyName"));
        return "estimates/projectEstimates";
    }

    @GetMapping("/project/{projectId}/estimates/{companyId}/{estimateId}")
    @SneakyThrows
    public String getEstimateById(@RequestAttribute String accessToken,
                                          @PathVariable int projectId,
                                          @PathVariable String companyId,
                                          @PathVariable int estimateId,
                                          Model model){
        var roles = authorizationService.getRoles(accessToken);

        if (roles.contains(Role.USER.get()))
            if (projectRepository.findById(projectId).isEmpty())
                return "redirect:/myprojects";
        if (roles.contains(Role.COMPANY.get()))
            if (projectsAndCompaniesRepository.findProjectAndCompaniesByProjectIdAndCompanyId(projectId, companyId).isEmpty())
                return "redirect:/myprojects";

        var estimate = fetchEstimatesService.getEstimate(estimateId);
        var ownerData = authorizationService.getAccountDtoById(companyId);
        var project = projectRepository.findById(projectId).orElseThrow(NotFoundException::new);
        var isOwner = project.getOwnerId().equals(authorizationService.getUserIdentity(accessToken).getId());

        System.out.println(isOwner);
        System.out.println(estimate.getStatus());
        model.addAttribute("isOwner", isOwner);
        model.addAttribute("estimate", estimate);
        model.addAttribute("ownerData", ownerData);
        return "estimates/estimate";
    }

    @GetMapping("/project/{projectId}/estimates/createestimate")
    @SneakyThrows
    public String getCreateEstimatePage(@RequestAttribute String accessToken,
                                        @PathVariable int projectId,
                                        Model model){
        var company = authorizationService.getAccountDtoByToken(accessToken);
        var companyIdentity = authorizationService.getUserIdentity(accessToken);
        var project = projectRepository.findById(projectId);
        if (project.isEmpty())
            throw new NotFoundException();
        if (projectsAndCompaniesRepository.findProjectAndCompaniesByProjectIdAndCompanyId(projectId, companyIdentity.getId()).isEmpty())
            return "redirect:/myprojects";

        model.addAttribute("ownerData", authorizationService.getAccountDtoById(project.get().getOwnerId()));
        model.addAttribute("companyName", company.getAttributes().get("companyName").get(0));
        model.addAttribute("projectId", projectId);

        return "estimates/createEstimate";
    }

    @PostMapping("/project/{projectId}/estimates/createestimate")
    @SneakyThrows
    public RedirectView createEstimate(@RequestAttribute String accessToken,
                                       @PathVariable int projectId,
                                       @RequestBody EstimateRequestDto estimateRequestDto){

        var company = authorizationService.getUserIdentity(accessToken);
        manageEstimateService.createEstimate(estimateRequestDto, projectId, company.getId());
        return new RedirectView("/project/"+projectId+"/estimates/"+company.getId());
    }

    @PostMapping("/project/{projectId}/estimates/{companyId}/{estimateId}/accept")
    @SneakyThrows
    public RedirectView acceptEstimate(@RequestAttribute String accessToken,
                                       @PathVariable int projectId,
                                       @PathVariable String companyId,
                                       @PathVariable int estimateId){
        var project = projectRepository.findById(projectId).orElseThrow(NotFoundException::new);
        var isOwner = authorizationService.getUserIdentity(accessToken).getId().equals(project.getOwnerId());

        if(!isOwner)
            return new RedirectView("/myprojects");

        manageEstimateService.acceptEstimate(estimateId);
        return new RedirectView("/project/"+projectId+"/estimates/"+companyId);
    }

    @PostMapping("/project/{projectId}/estimates/{companyId}/{estimateId}/decline")
    @SneakyThrows
    public RedirectView declineEstimate(@RequestAttribute String accessToken,
                                       @PathVariable int projectId,
                                       @PathVariable String companyId,
                                       @PathVariable int estimateId){
        var project = projectRepository.findById(projectId).orElseThrow(NotFoundException::new);
        var isOwner = authorizationService.getUserIdentity(accessToken).getId().equals(project.getOwnerId());

        if(!isOwner)
            return new RedirectView("/myprojects");

        manageEstimateService.declineEstimate(estimateId);
        return new RedirectView("/project/"+projectId+"/estimates/"+companyId);
    }
}
