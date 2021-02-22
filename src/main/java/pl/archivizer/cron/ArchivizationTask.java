package pl.archivizer.cron;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.scheduling.config.ScheduledTask;
import pl.archivizer.models.ArchivizeAfter;
import pl.archivizer.models.FileWithMetadata;
import pl.archivizer.repository.FileWithMetadataRepository;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

@RequiredArgsConstructor
@Configuration
@EnableScheduling
public class ArchivizationTask {
    private static final Logger log = LoggerFactory.getLogger(ScheduledTask.class);
    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");

    private final FileWithMetadataRepository fileWithMetadataRepository;

    @Scheduled(cron = "#{@expresion}")
    public void archivizationTask() {
        Date actualDate = new Date();
        log.info("Archivization time: " + actualDate);
        List<FileWithMetadata> filesToCheck = fileWithMetadataRepository.findAllByQualification_CanBeDeletedTrue();
        ArchivizeAfter archivizeAfter = null;
        for (FileWithMetadata file : filesToCheck){
            archivizeAfter = file.getQualification().getArchivizeAfter();
            if(!(archivizeAfter == null) & !file.isCanBeDeleted()) {

                Calendar creationDate = Calendar.getInstance();
                creationDate.setTime(file.getCreationDate());

                Calendar deletionDate = (Calendar) creationDate.clone();
                deletionDate.add(Calendar.YEAR, archivizeAfter.getYear());
                deletionDate.add(Calendar.MONTH, archivizeAfter.getMonth());
                deletionDate.add(Calendar.DATE, archivizeAfter.getDay());

                log.info(deletionDate.getTime().toString());
                log.info(creationDate.getTime().toString());
                if(deletionDate.before(Calendar.getInstance())){
                    file.setCanBeDeleted(true);
                    fileWithMetadataRepository.save(file);
                }

            }

        }

    }
}
