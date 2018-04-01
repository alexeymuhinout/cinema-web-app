package com.rustedbrain.study.course.service.util;

import com.rustedbrain.study.course.model.dto.TicketInfo;
import freemarker.template.*;

import java.io.IOException;
import java.io.Writer;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

public class TicketInfoHTML {

    private static final String TEMPLATES_PATH = "/home/alexey/IdeaProjects/cinema-web-app/target/classes";
    private static final String DEFAULT_ENCODING = "UTF-8";
    private static final String TICKET_TEMPLATE = "ticket.ftl";


    private static TicketInfoHTML ourInstance = new TicketInfoHTML();
    private Configuration configuration;

    private TicketInfoHTML() {
        try {
            configuration = new Configuration(new Version(2, 3, 20));
            configuration.setDirectoryForTemplateLoading(Paths.get(TEMPLATES_PATH).toFile());
            // Some other recommended settings:
            configuration.setDefaultEncoding(DEFAULT_ENCODING);
            configuration.setLocale(Locale.US);
            configuration.setTemplateExceptionHandler(TemplateExceptionHandler.RETHROW_HANDLER);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static TicketInfoHTML getInstance() {
        return ourInstance;
    }


    public void precess(List<TicketInfo> tickets, Writer writer) throws IOException, TemplateException {
        Template template = configuration.getTemplate(TICKET_TEMPLATE);

        template.process(new HashMap<String, Object>() {
            {
                put("tickets", tickets);
            }
        }, writer);
    }
}
