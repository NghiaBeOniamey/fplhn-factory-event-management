package fplhn.udpm.identity.infrastructure.excel.jobconfig.role;

import fplhn.udpm.identity.entity.StaffRoleModule;
import fplhn.udpm.identity.infrastructure.excel.commonio.role.RoleProcessor;
import fplhn.udpm.identity.infrastructure.excel.commonio.role.RoleRowMapper;
import fplhn.udpm.identity.infrastructure.excel.commonio.role.RoleWriter;
import fplhn.udpm.identity.infrastructure.excel.model.ImportRoleExcel;
import fplhn.udpm.identity.infrastructure.excel.repository.RoleExcelRepository;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.core.explore.JobExplorer;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.launch.JobOperator;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.ExecutionContext;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.excel.RowMapper;
import org.springframework.batch.item.excel.poi.PoiItemReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import java.io.File;
import java.util.List;

@Configuration
@Slf4j
@EnableTransactionManagement
public class RoleJobConfig {

    @Setter(onMethod_ = @Autowired, onParam_ = @Qualifier("transactionManager"))
    private PlatformTransactionManager transactionManager;


    @Value("${file.upload-excel-role}")
    private String pathSaveExcel;

    @Setter(onMethod_ = @Autowired)
    private RoleExcelRepository roleExcelRepository;

    @Bean
    @StepScope
    ItemReader<ImportRoleExcel> excelRoleReader(@Value("#{jobParameters['fullPathFileNameRole']}") String nameFile) {
        try {
            PoiItemReader<ImportRoleExcel> reader = new PoiItemReader<>();
            Resource resource = new FileSystemResource(new File(pathSaveExcel + nameFile));
            if (!resource.exists()) throw new RuntimeException("Could not read the file!");
            reader.setResource(resource);
            reader.setLinesToSkip(1);
            reader.open(new ExecutionContext());
            reader.setRowMapper(rowMapper());
            reader.setLinesToSkip(1);
            return reader;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @StepScope
    private RowMapper<ImportRoleExcel> rowMapper() {
        int count = (int) roleExcelRepository.count();
        return new RoleRowMapper(count);
    }

    @StepScope
    @Bean
    @Qualifier("roleProcessor")
    ItemProcessor<ImportRoleExcel, List<StaffRoleModule>> roleProcessor() {
        return new RoleProcessor();
    }

    @StepScope
    @Bean
    ItemWriter<List<StaffRoleModule>> excelRoleWriter() {
        return new RoleWriter();
    }

    @Bean
    Step excelFileToDatabaseStepRole(@Qualifier("excelRoleReader") ItemReader<ImportRoleExcel> excelRequestItemReader
            , JobRepository jobRepository) {
        return new StepBuilder("Excel-File-To-Database-Step-ROLE", jobRepository)
                .<ImportRoleExcel, List<StaffRoleModule>>chunk(100, transactionManager)
                .reader(excelRequestItemReader)
                .processor(item -> roleProcessor().process(item))
                .writer(chunk -> excelRoleWriter().write(chunk))
                .build();
    }

    @Bean
    Job excelFileToDatabaseJobRole(
            JobRepository jobRepository,
            @Qualifier("excelFileToDatabaseStepRole") Step excelFileToDatabaseStep,
            @Qualifier("jobOperator") JobOperator jobOperator,
            JobExplorer jobExplorer
    ) {
        return new JobBuilder("Excel-File-To-Database-Job-Role", jobRepository)
                .start(excelFileToDatabaseStep)
                .build();
    }

}
