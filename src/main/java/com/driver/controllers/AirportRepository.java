package com.driver.controllers;

import com.driver.model.Airport;
import com.driver.model.City;
import com.driver.model.Flight;
import com.driver.model.Passenger;
import io.swagger.models.auth.In;
import org.springframework.stereotype.Repository;

import java.util.*;

@Repository
public class AirportRepository {
    public HashMap<String,Airport> airportHashmap = new HashMap<>();
    public HashMap<Integer,Flight> flightHashMap = new HashMap<Integer, Flight>();
    public HashMap<Integer, Passenger> passengerHashMap = new HashMap<>();
    public HashMap<Integer,List<Integer>> ticketHashMap = new HashMap<>();

    public void addAirport(Airport airport){
        airportHashmap.put(airport.getAirportName(),airport);
    }

    public String getLargestAirportName(){
        String ans = "";
        int maxTerminals = 0;
        for(Airport airport : airportHashmap.values()){
            if(airport.getNoOfTerminals() > maxTerminals){
                maxTerminals = airport.getNoOfTerminals();
                ans = airport.getAirportName();
            }else if(airport.getNoOfTerminals() == maxTerminals){
                if(airport.getAirportName().compareTo(ans)<0){
                    ans = airport.getAirportName();
                }
            }
        }
        return ans;
    }

    public void addFlight(Flight flight){
        flightHashMap.put(flight.getFlightId(),flight);
    }


    public double getShortestDurationOfPossibleBetweenTwoCities(City fromCity, City toCity){

        double ans = 1000000000;
        for(Flight flight : flightHashMap.values()){
            if((flight.getFromCity().equals(flight)) && (flight.getToCity().equals(toCity))){
                ans = Math.min(ans,flight.getDuration());
            }
        }
        if(ans == 1000000000){
            return -1;
        }
        return  ans;
    }

    public void addPassenger(Passenger passenger){
        passengerHashMap.put(passenger.getPassengerId(),passenger);
    }

    public String bookATicket(int flightId, int passengerId){
        if(Objects.nonNull(ticketHashMap.get(flightId)) &&(ticketHashMap.get(flightId).size()<flightHashMap.get(flightId).getMaxCapacity())){
            List<Integer> passengers = ticketHashMap.get(flightId);
            if(passengers.contains(passengerId)){
                return "FAILURE";
            }
            passengers.add(passengerId);
            ticketHashMap.put(flightId,passengers);
            return "SUCCESS";
        } else if(Objects.isNull(ticketHashMap.get(flightId))){
            ticketHashMap.put(flightId,new ArrayList<>());
            List<Integer> passengers = ticketHashMap.get(flightId);
            if(passengers.contains(passengerId)){
                return "FAILURE";
            }
            passengers.add(passengerId);
            ticketHashMap.put(flightId,passengers);
            return "SUCCESS";
        }
        return "FAILURE";
    }

    public String cancelATicket(int flightId, int passengerId){
        List<Integer> passengers = ticketHashMap.get(flightId);
        if(passengers == null){
            return "FAILURE";
        }


        if(passengers.contains(passengerId)){
            passengers.remove(passengerId);
            return "SUCCESS";
        }
        return "FAILURE";
    }

    public int countOfBookingsDoneByPassengerAllCombined(int passengerId){
        HashMap<Integer,List<Integer>> passengerTicketsHashMap = new HashMap<>();
        int count = 0;
        for(Map.Entry<Integer,List<Integer>> entry : ticketHashMap.entrySet()){
            List<Integer> passengers = entry.getValue();
            for(Integer passenger : passengers){
                if(passenger == passengerId){
                    count++;
                }
            }
        }
        return count;
    }

    public int calculateFlightFare(int flightId){
        int noOfPeopleBooked = ticketHashMap.get(flightId).size();
        return noOfPeopleBooked*50 + 3000;
    }

    public String getAirportNameFromFlightId(int flightId){
        if(flightHashMap.containsKey(flightId)){
            City city = flightHashMap.get(flightId).getFromCity();
            for(Airport airport : airportHashmap.values()){
                if(airport.getCity().equals(city)){
                    return airport.getAirportName();
                }
            }
        }
        return null;
    }

    public int calculateRevenueOfAFlight(int flightId){
        int noOfPeopleBooked = ticketHashMap.get(flightId).size();
        int variableFare = (noOfPeopleBooked*(noOfPeopleBooked+1))*25;
        int fixedFare = 3000*noOfPeopleBooked;
        int totalFare = variableFare + fixedFare;

        return totalFare;
    }

    public int getNumberOfPeopleOn(Date date, String airportName){
        Airport airport = airportHashmap.get(airportName);
        if(Objects.isNull(airport)){
            return 0;
        }
        City city = airport.getCity();
        int count = 0;
        for(Flight flight:flightHashMap.values()){
            if(date.equals(flight.getFlightDate()))
                if(flight.getToCity().equals(city)||flight.getFromCity().equals(city)){

                    int flightId = flight.getFlightId();
                    count = count + ticketHashMap.get(flightId).size();
                }
        }
        return count;
    }
}
