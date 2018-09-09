package com.rustedbrain.study.course.service.resources;

import java.io.File;
import java.io.IOException;
import java.util.Map;
import java.util.Objects;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.xml.bind.JAXBException;
import javax.xml.parsers.ParserConfigurationException;

import org.springframework.stereotype.Component;
import org.xml.sax.SAXException;

import com.rustedbrain.study.course.model.exception.ResourceException;
import com.rustedbrain.study.course.service.resources.xml.CinemaHallParser;
import com.rustedbrain.study.course.service.resources.xml.HelpParser;

@Component
public class ResourceAccessor {

	private static final Logger LOG = Logger.getLogger(ResourceAccessor.class.getName());

	private static final String IP_GEO_DATABASE_RESOURCE = "GeoLiteCity.dat";
	private static final String HELP_FILE_NAME = "help.xml";

	private static final String CINEMA_HALL_FILE_NAME = "cinemaHall.xml";

	public Map<String, String> getHelpTittleTextMap()
			throws ParserConfigurationException, IOException, SAXException, ResourceException {
		try {
			return new HelpParser().getHelpTittleTextMap(getResourceFile(HELP_FILE_NAME));
		} catch (JAXBException e) {
			LOG.log(Level.WARNING, "Error occurred during getting info for help page", e);
			throw new ResourceException(e);
		}
	}

	public Map<Integer, Integer> getCinemaHallSeatMap()
			throws ParserConfigurationException, IOException, SAXException, ResourceException {

		try {
			return new CinemaHallParser().getCinemaHallSeatMap(getResourceFile(CINEMA_HALL_FILE_NAME));
		} catch (JAXBException e) {
			LOG.log(Level.WARNING, "Error occurred during getting info for cinema hall constructor page", e);
			throw new ResourceException(e);
		}
	}

	public File getIPGeoDatabaseFile() {
		return new File(
				Objects.requireNonNull(getClass().getClassLoader().getResource(IP_GEO_DATABASE_RESOURCE)).getFile());
	}

	private File getResourceFile(String fileName) {
		return new File(Objects.requireNonNull(getClass().getClassLoader().getResource(fileName)).getFile());
	}

}
