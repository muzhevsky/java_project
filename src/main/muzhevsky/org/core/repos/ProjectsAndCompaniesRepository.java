package muzhevsky.org.core.repos;

import muzhevsky.org.core.entities.ProjectAndCompany;
import org.springframework.data.jdbc.repository.query.Modifying;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import javax.ws.rs.QueryParam;
import java.util.List;

@Repository
public interface ProjectsAndCompaniesRepository extends CrudRepository<ProjectAndCompany, Integer> {
    List<ProjectAndCompany> findProjectAndCompaniesByProjectIdAndCompanyId(int projectId, String companyId);
    List<ProjectAndCompany> findProjectAndCompaniesByCompanyId(String companyId);
    List<ProjectAndCompany> findProjectAndCompaniesByProjectId(int projectId);

    @Modifying
    @Query("insert into projects_and_companies (project_id, company_id) values(:projectId, :companyId)")
    void createProjectAndCompanyRecord(@QueryParam("proejctId") int projectId, @QueryParam("companyId") String companyId);
}
