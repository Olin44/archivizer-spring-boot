//package pl.archivizer.cron;
//
//import org.springframework.context.annotation.Configuration;
//import org.springframework.scheduling.annotation.EnableScheduling;
//import org.springframework.scheduling.annotation.SchedulingConfigurer;
//import org.springframework.scheduling.config.ScheduledTaskRegistrar;
//
//import java.time.LocalDateTime;
//
//@Configuration
//@EnableScheduling
//public class SchedulerConfig implements SchedulingConfigurer {
//    private final ArchivizationTask scheduledTask;
//
//    public SchedulerConfig(ArchivizationTask scheduledTask) {
//        this.scheduledTask = scheduledTask;
//    }
//
//    @Override
//    public void configureTasks(ScheduledTaskRegistrar taskRegistrar) {
//        final LocalDateTime dateChange = LocalDateTime.now().plusSeconds(20);
//        taskRegistrar.addFixedRateTask(() -> {
//            if (LocalDateTime.now().isAfter(dateChange)) {
//                scheduledTask.executeTask();
//            }
//        }, 1000);
//    }
//}