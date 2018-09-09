package com.rustedbrain.study.course.service.resources.xml;

import java.io.File;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;

import com.rustedbrain.study.course.model.dto.xml.Coordinate;
import com.rustedbrain.study.course.model.dto.xml.Row;
import com.rustedbrain.study.course.model.dto.xml.Rows;
import com.rustedbrain.study.course.model.dto.xml.Seat;

public class CinemaHallParser {
	public void marshaling() {

		Coordinate coordinate = new Coordinate();
		coordinate.setX(0);
		coordinate.setY(0);
		Seat firstSeat = new Seat();
		firstSeat.setCoordinate(coordinate);
		Seat secondSeat = new Seat();
		secondSeat.setCoordinate(coordinate);
		Seat thirdSeat = new Seat();
		thirdSeat.setCoordinate(coordinate);
		List<Seat> seats = Arrays.asList(firstSeat, secondSeat, thirdSeat);

		Row firstRow = new Row();
		firstRow.setSeats(seats);
		Row secondRow = new Row();
		secondRow.setSeats(seats);
		Row thirdRow = new Row();
		thirdRow.setSeats(seats);

		List<Row> rowsList = Arrays.asList(firstRow, secondRow, thirdRow);

		Rows rows = new Rows();
		rows.setRows(rowsList);

		try {

			File file = new File("src/main/resources/cinemaHall.xml");
			JAXBContext jaxbContext = JAXBContext.newInstance(Rows.class);
			Marshaller jaxbMarshaller = jaxbContext.createMarshaller();

			// output pretty printed
			jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);

			jaxbMarshaller.marshal(rows, file);
			jaxbMarshaller.marshal(rows, System.out);

		} catch (JAXBException e) {
			e.printStackTrace();
		}
	}

	public Map<Integer, Integer> getCinemaHallSeatMap(File file) throws JAXBException {
		Unmarshaller jaUnmarshaller = JAXBContext.newInstance(Rows.class).createUnmarshaller();
		Rows rows = (Rows) jaUnmarshaller.unmarshal(file);

		Map<Integer, Integer> cinemaHallSeatMap = new HashMap<>();

		for (Row row : rows.getRows()) {
			for (Seat seat : row.getSeats()) {
				cinemaHallSeatMap.put(seat.getCoordinate().getX(), seat.getCoordinate().getY());
			}
		}
		return cinemaHallSeatMap;
	}

}
