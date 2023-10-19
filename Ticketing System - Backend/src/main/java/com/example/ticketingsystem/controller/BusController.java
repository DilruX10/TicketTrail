/**
 * Author: IT21118340
 **/

package com.example.ticketingsystem.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.ticketingsystem.common.CommonConstants;
import com.example.ticketingsystem.model.Bus;
import com.example.ticketingsystem.services.BusService;
import com.google.gson.Gson;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
@RequestMapping(CommonConstants.ENDPOINT_BUS)
public class BusController {
	
	private BusService busService;
	private Gson gsonObj;
	
	/**
	 * Constructor for BusController class
	 * 
	 * @param busService
	 * @param gsonObj
	 */
	@Autowired 
	public BusController(BusService busService) {
		
		this.busService = busService;
		this.gsonObj = new Gson();
	}
	
	/**
	 * This GET endpoint that return list of all buses as a JSON object. 
	 * 
	 * @return responseEntity - A ResponseEntity containing list of all buses and their details in JSON format and an HTTP status of OK.
	 * 
	 */
	@RequestMapping(value = CommonConstants.METHOD_VIEW_ALL, method = RequestMethod.GET, produces="application/json")
	public ResponseEntity<String> getAllBuses() {
		
		// Uses the Google GSON library to convert the list of buses into a JSON string
		try {
			return new ResponseEntity<String>(gsonObj.toJson(busService.allBuses()), HttpStatus.OK);
			
		} catch (Exception e) {
			
			return new ResponseEntity<String>(CommonConstants.MSG_ERROR_COMMON, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	/**
	 * This method retrieve a specific bus. 
	 * It is a GET endpoint that accepts a single path variable and produces JSON data.
	 * 
	 * @param id - given as a path variable
	 * @return responseEntity - A ResponseEntity containing the bus details in JSON format and an HTTP status of OK.
	 * 
	 */
	@RequestMapping(value = CommonConstants.METHOD_VIEW_ONE, method = RequestMethod.GET, produces="application/json")  
	public ResponseEntity<String> getOneBus(@PathVariable String id) {
		
		try {

			return new ResponseEntity<String>(gsonObj.toJson(busService.oneBus(id)), HttpStatus.OK);
						 
		} catch (Exception e) {
			
			return new ResponseEntity<String>(CommonConstants.MSG_ERROR_COMMON, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
	}
	
	/**
	 * This method creates a new bus. 
	 * It is a POST endpoint that consumes and produces JSON data.
	 * 
	 * @param movie - The bus details provided in the request body.
	 * @return responseEntity - A ResponseEntity containing the created bus details in JSON format and an HTTP status of OK.
	 * 
	 */
	@RequestMapping(value = CommonConstants.METHOD_NEW, method = RequestMethod.POST, produces="application/json", consumes="application/json")
	@ResponseBody
	public ResponseEntity<String> addBus(@RequestBody Bus bus) {
		
		try {
			
			if(busService.insertBus(bus)) {
				
				return new ResponseEntity<String>(CommonConstants.MSG_SUCCESS_INSERT, HttpStatus.OK);
				
			} else {
				
				/*
				 * The 422 (Unprocessable Entity) status code means the server understands the content type of the request entity (hence a 415 (Unsupported Media Type) 
				 * status code is inappropriate), and the syntax of the request entity is correct (thus a 400 (Bad Request) status code is inappropriate) 
				 * but was unable to process the contained instructions. For example, this error condition may occur if an JSON request body contains 
				 * well-formed (i.e., syntactically correct), but semantically erroneous, data.
				 */				
				return new ResponseEntity<String>(CommonConstants.MSG_ERROR_INSERT, HttpStatus.UNPROCESSABLE_ENTITY);
			}
			
		} catch (Exception e) {

			return new ResponseEntity<String>(CommonConstants.MSG_ERROR_INSERT, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	/**
	 * This method is used to update the details of a bus in the db.
	 * It is a PUT endpoint that consumes and produces JSON data.
	 *
	 * @param movie - The bus object to be updated.
	 * @return responseEntity-  A ResponseEntity containing the updated bus details in JSON format and an HTTP status of OK.
	 *
	 */
	@RequestMapping(value = CommonConstants.METHOD_UPDATE, method = RequestMethod.PUT, produces="application/json", consumes="application/json")
	public ResponseEntity<String> editBus(@RequestBody Bus bus) {
		
		try {
			
			if(busService.updateBus(bus)) {
				
				return new ResponseEntity<String>(CommonConstants.MSG_SUCCESS_UPDATE, HttpStatus.OK);
				
			} else {
				
				return new ResponseEntity<String>(CommonConstants.MSG_NOT_FOUND, HttpStatus.UNPROCESSABLE_ENTITY);
			}
			
		} catch (Exception e) {
			
			return new ResponseEntity<String>(CommonConstants.MSG_ERROR_UPDATE, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	/**
	 * This method is used to delete a bus by their ID.
	 * It is a DELETE endpoint that accepts a single path variable and produces JSON data.
	 *
	 * @param id - path variable
	 * @return responseEntity
	 *
	 */
	@RequestMapping(value = CommonConstants.METHOD_DELETE, method = RequestMethod.DELETE, produces="application/json")
	public ResponseEntity<String> deleteBus(@PathVariable String id) {
		
		try {
			if(busService.deleteBus(id)) {
				
				return new ResponseEntity<String>(CommonConstants.MSG_SUCCESS_DELETE, HttpStatus.OK);
				
			} else {
				
				return new ResponseEntity<String>(CommonConstants.MSG_NOT_FOUND, HttpStatus.UNPROCESSABLE_ENTITY);
			}
		} catch (Exception e) {
			
			return new ResponseEntity<String>(CommonConstants.MSG_ERROR_DELETE, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

}
