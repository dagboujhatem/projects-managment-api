package com.siga.restapi;

import com.siga.restapi.entities.*;
import com.siga.restapi.repositories.CountryRepository;
import com.siga.restapi.repositories.ProjectRepository;
import com.siga.restapi.repositories.TaskRepository;
import com.siga.restapi.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.ArrayList;
import java.util.List;

@SpringBootApplication
@EnableSwagger2
public class RestapiApplication implements CommandLineRunner {

	@Autowired
	UserRepository userRepository;

	@Autowired
	ProjectRepository projectRepository;

	@Autowired
	TaskRepository taskRepository;

	@Autowired
	CountryRepository countryRepository;

	@Autowired
	private ApplicationContext applicationContext;

	public static void main(String[] args) {
		SpringApplication.run(RestapiApplication.class, args);
	}

	// this bean used to crypt the password
	public BCryptPasswordEncoder passwordEncoder() {
		BCryptPasswordEncoder passwordEncoderBean = applicationContext.getBean(BCryptPasswordEncoder.class);
		return passwordEncoderBean;
	}

	@Override
	public void run(String... args) throws Exception {
		// Default users
		this.userRepository.deleteAll();

		User defaultUser = new User();
		defaultUser.setFirstName("Wided");
		defaultUser.setLastName("Arfaoui");
		defaultUser.setEmail("widedarfaoui6@gmail.com");
		defaultUser.setPassword(this.passwordEncoder().encode("123456789"));
		defaultUser.setRole(ERole.ROLE_MANAGER);
		this.userRepository.save(defaultUser);

		// Default countries
		this.countryRepository.deleteAll();
		Country tunisia = new Country();
		tunisia.setLibelle("Tunisie");

		Country france = new Country();
		france.setLibelle("France");

		List<Country> listCountries = new ArrayList<>();
		listCountries.add(tunisia);
		listCountries.add(france);
		this.countryRepository.saveAll(listCountries);

		// Default projects
		this.projectRepository.deleteAll();
		Project project1 = new Project();
		project1.setCode("001");
		project1.setDescription("Project 1");
		project1.setState(EState.IN_PROGRESS);
		project1.setPays(tunisia);
		project1.setPrix("5400 DTN");
		project1.setStartDate("2022-06-25");
		project1.setEndDate("2022-06-30");
		Project savedProject = this.projectRepository.save(project1);

		// Default tasks
		this.taskRepository.deleteAll();
		Task task1 = new Task();
		task1.setCode("001");
		task1.setLibelle("Task 1");
		task1.setProject(savedProject);
		Task task2 = new Task();
		task2.setCode("002");
		task2.setLibelle("Task 2");
		task2.setProject(savedProject);

		List<Task> listTasks = new ArrayList<>();
		listTasks.add(task1);
		listTasks.add(task2);
		this.taskRepository.saveAll(listTasks);

	}
}
