package com.ivo.test.coq.project;

import com.ivo.coq.project.entity.Project;
import com.ivo.coq.project.repository.ProjectRepository;
import com.ivo.test.AbstractTest;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;

import java.util.List;

/**
 * @author wj
 * @version 1.0
 */
public class ProjectServiceTest extends AbstractTest {

    @Autowired
    private ProjectRepository repository;

    @Test
    public void test() {}

    @Test
    public void createProject() {
        Project project = new Project();
        project.setProject("N1408 R0");
        project.setType("TOP");
        project.setSize("2.3");
        repository.save(project);

        List list = repository.findAll();
        Assert.notEmpty(list, "ERROR");
    }
}
