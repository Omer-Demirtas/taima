package com.quartz.quartzexample.service.impl;

import com.quartz.quartzexample.dto.QrtzJobStateTrackerDTO;
import com.quartz.quartzexample.model.JobTracking;
import com.quartz.quartzexample.repository.QuartzJobTrackingRepository;
import com.quartz.quartzexample.service.QuartzJobTrackingService;
import com.quartz.quartzexample.utils.DateFormatConverter;
import org.springframework.stereotype.Service;

@Service
public class QuartzJobTrackingServiceImpl implements QuartzJobTrackingService
{
    private final QuartzJobTrackingRepository quartzJobTrackingRepository;
    private final DateFormatConverter dateFormatConverter;

    public QuartzJobTrackingServiceImpl(QuartzJobTrackingRepository quartzJobTrackingRepository, DateFormatConverter dateFormatConverter) {
        this.quartzJobTrackingRepository = quartzJobTrackingRepository;
        this.dateFormatConverter = dateFormatConverter;
    }

    @Override
    public JobTracking findFirstByJobName(String jobName) {
        return quartzJobTrackingRepository.findFirstByJobName(jobName);
    }

    @Override
    public JobTracking save(JobTracking qrtzJobStateTracker) {
        return quartzJobTrackingRepository.save(qrtzJobStateTracker);
    }

    @Override
    public QrtzJobStateTrackerDTO getUpdateJobState() {
        /*
        JobTracking qrtzJobStateTracker = quartzJobTrackingRepository.findFirstByJobName(Constants.jobNameUpdate);

        if (qrtzJobStateTracker == null)
            throw new IllegalArgumentException("update job can not found");

        formatProcess(qrtzJobStateTracker);
        return new QrtzJobStateTrackerDTO(qrtzJobStateTracker);

         */
        return null;
    }

    @Override
    public QrtzJobStateTrackerDTO getUpdateJobState(String jobName) {
        JobTracking qrtzJobStateTracker = quartzJobTrackingRepository.findFirstByJobName(jobName);

        if (qrtzJobStateTracker == null)
            throw new IllegalArgumentException("update job can not found with name " + jobName);

        formatProcess(qrtzJobStateTracker);
        return new QrtzJobStateTrackerDTO(qrtzJobStateTracker);
    }

    private void formatProcess(JobTracking qrtzJobStateTracker) {
        /*
        String lastUpdate = qrtzJobStateTracker.getLastUpdate();
        String[] values = lastUpdate.split("T");
        lastUpdate = values[0] + " " + values[1].substring(0, 5);

        final String NEW_FORMAT = "dd/MM/yyyy hh:mm";
        final String OLD_FORMAT = "yyyyy-mm-dd hh:mm";
        String oldDateString = lastUpdate;
        String newDateString = dateFormatConverter.formatDate(NEW_FORMAT, OLD_FORMAT, oldDateString);

        qrtzJobStateTracker.setLastUpdate(newDateString);

         */
    }
}
