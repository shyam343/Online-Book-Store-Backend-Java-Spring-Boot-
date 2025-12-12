package com.ragnar.main.Util.Helpers;

import com.ragnar.main.Application.IRepositories.ILogRepository;
import com.ragnar.main.Domain.Entities.Logs;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.logging.LogLevel;
import org.springframework.stereotype.Service;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class DbLogger {
    private final ILogRepository logRepository;

    public void log(LogLevel level, String message, String loggerName, Throwable exception) {
        String stackTrace = null;

        if (exception != null) {
            StringWriter sw = new StringWriter();
            PrintWriter pw = new PrintWriter(sw);
            exception.printStackTrace(pw);
            stackTrace = sw.toString();
        }

        Logs log = Logs.builder()
                .level(level)
                .message(message)
                .logger(loggerName)
                .timestamp(LocalDateTime.now())
                .exception(stackTrace)
                .build();

        logRepository.save(log);
    }
}
