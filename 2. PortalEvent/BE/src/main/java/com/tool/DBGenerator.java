//package com.tool;
//
//import com.portalevent.entity.*;
//import com.portalevent.entity.Object;
//import com.portalevent.infrastructure.constant.*;
//import com.portalevent.repository.*;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.CommandLineRunner;
//import org.springframework.boot.SpringApplication;
//import org.springframework.boot.autoconfigure.SpringBootApplication;
//import org.springframework.context.ConfigurableApplicationContext;
//import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
//
//import java.util.Date;
//
///**
// * @author SonPT
// */
//@SpringBootApplication
//@EnableJpaRepositories(basePackages = "com.portalevent.repository")
//public class DBGenerator implements CommandLineRunner {
//
//	@Autowired
//    private AgendaItemRepository agendaItemRepository;
//    @Autowired
//    private CategoryRepository categoryRepository;
//    @Autowired
//    private CommentRepository commentRepository;
//    @Autowired
//    private EventLocationRepository eventLocationRepository;
//    @Autowired
//    private EventMajorRepository eventMajorRepository;
//    @Autowired
//    private EventObjectRepository eventObjectRepository;
//    @Autowired
//    private EventOrganizerRepository eventOrganizerRepository;
//    @Autowired
//    private EventRepository eventRepository;
//    @Autowired
//    private EvidenceRepository evidenceRepository;
//    @Autowired
//    private MajorRepository majorRepository;
//    @Autowired
//    private ObjectRepository objectRepository;
//    @Autowired
//    private OrganizerMajorRepository organizerMajorRepository;
//    @Autowired
//    private ParticipantRepository participantRepository;
//    @Autowired
//    private PeriodicEventMajorRepository periodicEventMajorRepository;
//    @Autowired
//    private PeriodicEventObjectRepository periodicEventObjectRepository;
//    @Autowired
//    private PeriodicEventRepository periodicEventRepository;
//    @Autowired
//    private ResourceRepository resourceRepository;
//    @Autowired
//    private SemesterRepository semesterRepository;
//    @Autowired
//    private SystemOptionRepository systemOptionRepository;
//
//
//    @Override
//    public void run(String... args) throws Exception {
//
//        //Bảng category
//        Category seminar = new Category();
//        seminar.setName("Seminar");
//        seminar.setId(categoryRepository.save(seminar).getId());
//        Category workshop = new Category();
//        workshop.setName("Workshop");
//        workshop.setId(categoryRepository.save(workshop).getId());
//        Category talkshow = new Category();
//        talkshow.setName("Talkshow");
//        talkshow.setId(categoryRepository.save(talkshow).getId());
//
//        //Bảng Semester
//        Semester fall2023 = new Semester();
//        fall2023.setName("Fall 2023");
//        fall2023.setStartTime(1693501200000L);
//        fall2023.setEndTime(1703955600000L);
//        fall2023.setStartTimeFirstBlock(1693587600000L);
//        fall2023.setEndTimeFirstBlock(1698598800000L);
//        fall2023.setStartTimeSecondBlock(1698858000000L);
//        fall2023.setEndTimeSecondBlock(1703869200000L);
//        fall2023.setId(semesterRepository.save(fall2023).getId());
//
//        Semester spring2024 = new Semester();
//        spring2024.setName("Spring 2024");
//        spring2024.setStartTime(1641049200000L);
//        spring2024.setEndTime(1648731600000L);
//        spring2024.setStartTimeFirstBlock(1641049200000L);
//        spring2024.setEndTimeFirstBlock(1646041200000L);
//        spring2024.setStartTimeSecondBlock(1646149200000L);
//        spring2024.setEndTimeSecondBlock(1648731600000L);
//        spring2024.setId(semesterRepository.save(spring2024).getId());
//
//        //Bảng Object
//        Object ky1 = new Object();
//        ky1.setName("Kỳ 1");
//        ky1.setId(objectRepository.save(ky1).getId());
//        Object ky2 = new Object();
//        ky2.setName("Kỳ 2");
//        ky2.setId(objectRepository.save(ky2).getId());
//        Object ky3 = new Object();
//        ky3.setName("Kỳ 3");
//        ky3.setId(objectRepository.save(ky3).getId());
//        Object ky4 = new Object();
//        ky4.setName("Kỳ 4");
//        ky4.setId(objectRepository.save(ky4).getId());
//        Object ky5 = new Object();
//        ky5.setName("Kỳ 5");
//        ky5.setId(objectRepository.save(ky5).getId());
//        Object ky6 = new Object();
//        ky6.setName("Kỳ 6");
//        ky6.setId(objectRepository.save(ky6).getId());
//        Object ky7 = new Object();
//        ky7.setName("Kỳ 7");
//        ky7.setId(objectRepository.save(ky7).getId());
//
//        // Bảng Major (bộ môn)
//        Major udpm = new Major();
//        udpm.setCode("UDPM");
//        udpm.setMainMajorId(null);
//        udpm.setName("Ứng dụng phần mềm");
//        udpm.setMailOfManager("truongson.dev@gmail.com");
//        udpm.setId(majorRepository.save(udpm).getId());
//
//        Major ptpm = new Major();
//        ptpm.setCode("PTPM");
//        ptpm.setMainMajorId(null);
//        ptpm.setName("Phát triển phần mềm");
//        ptpm.setMailOfManager("truongson.dev@gmail.com");
//        ptpm.setId(majorRepository.save(ptpm).getId());
//
//        Major xldl = new Major();
//        xldl.setCode("XLDL");
//        xldl.setMainMajorId(null);
//        xldl.setName("Xử lý dữ liệu");
//        xldl.setMailOfManager("truongson.dev@gmail.com");
//        xldl.setId(majorRepository.save(xldl).getId());
//
//        Major javaMajor = new Major();
//        javaMajor.setCode("PTPM_Java");
//        javaMajor.setMainMajorId(udpm.getId());
//        javaMajor.setName("bộ môn hẹp java");
//        javaMajor.setMailOfManager("truongson.dev@gmail.com");
//        javaMajor.setId(majorRepository.save(javaMajor).getId());
//
//        Major cSharpMajor = new Major();
//        cSharpMajor.setCode("PTPM_C");
//        cSharpMajor.setMainMajorId(udpm.getId());
//        cSharpMajor.setName("Phát triển phần mềm C Sharp");
//        cSharpMajor.setMailOfManager("truongson.dev@gmail.com");
//        cSharpMajor.setId(majorRepository.save(cSharpMajor).getId());
//
//        Semester fall2024 = new Semester();
//        fall2024.setName("Fall 2024");
//        fall2024.setStartTime(1745643600000L);
//        fall2024.setEndTime(1756098000000L);
//        fall2024.setStartTimeFirstBlock(1745730000000L);
//        fall2024.setEndTimeFirstBlock(1750741200000L);
//        fall2024.setStartTimeSecondBlock(1751000400000L);
//        fall2024.setEndTimeSecondBlock(1756011600000L);
//        fall2024.setId(semesterRepository.save(fall2024).getId());
//
//        Semester spring2025 = new Semester();
//        spring2025.setName("Spring 2025");
//        spring2025.setStartTime(1693194000000L);
//        spring2025.setEndTime(1700876400000L);
//        spring2025.setStartTimeFirstBlock(1693194000000L);
//        spring2025.setEndTimeFirstBlock(1698186000000L);
//        spring2025.setStartTimeSecondBlock(1698294000000L);
//        spring2025.setEndTimeSecondBlock(1700876400000L);
//        spring2025.setId(semesterRepository.save(spring2025).getId());
//    }
//
//    public static void main(String[] args) {
//        ConfigurableApplicationContext ctx = SpringApplication.run(DBGenerator.class);
//        ctx.close();
//    }
//}
