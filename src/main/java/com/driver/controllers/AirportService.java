package com.driver.controllers;

import com.driver.model.Airport;
import com.driver.model.City;
import com.driver.model.Flight;
import com.driver.model.Passenger;
import org.springframework.stereotype.Service;


import java.util.*;
@Service
public class AirportService {
    AirportRepository airportRepository = new AirportRepository();

    public void addAirport(Airport airport){
        airportRepository.addAirport(airport);
    }

    public String getLargestAirportName(){
        String name = airportRepository.getLargestAirportName();
        return name;
    }

    public void addFlight(Flight flight){
        airportRepository.addFlight(flight);
    }

    public double getShortestDurationOfPossibleBetweenTwoCities(City fromCity, City toCity){
        double ans = airportRepository.getShortestDurationOfPossibleBetweenTwoCities(fromCity,toCity);
        return ans;
    }

    public void addPassenger(Passenger passenger){
        airportRepository.addPassenger(passenger);
    }

    public String bookATicket(int flightId, int passengerId){
        String ans = airportRepository.bookATicket(flightId,passengerId);
        return ans;
    }

    public String cancelATicket(int flightId, int passengerId){
        String ans = airportRepository.cancelATicket(flightId,passengerId);
        return ans;
    }

    public int countOfBookingsDoneByPassengerAllCombined(int passengerId){
        int ans = airportRepository.countOfBookingsDoneByPassengerAllCombined(passengerId);
        return ans;
    }

    public int calculateFlightFare(int flightId){
        int ans = airportRepository.calculateFlightFare(flightId);
        return ans;
    }

    public String getAirportNameFromFlightId(int flightId){
        String ans = airportRepository.getAirportNameFromFlightId(flightId);
        return ans;
    }

    public int calculateRevenueOfAFlight(int flightId){
        int ans = airportRepository.calculateRevenueOfAFlight(flightId);
        return ans;
    }

    public int getNumberOfPeopleOn(Date date, String airportName){
        int ans = airportRepository.getNumberOfPeopleOn(date,airportName);
        return ans;
    }
}
