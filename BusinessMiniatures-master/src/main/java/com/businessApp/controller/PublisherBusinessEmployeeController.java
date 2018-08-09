package com.businessApp.controller;

import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.businessApp.bean.AddPto;
import com.businessApp.bean.AppointmentBean;
import com.businessApp.bean.ResponseBean;
import com.businessApp.constants.StatusConstants;
import com.businessApp.model.AppointmentCalendar;
import com.businessApp.model.EmployeePto;
import com.businessApp.model.PublisherBusinessEmployee;
import com.businessApp.service.PublisherBusinessEmployeeService;

@RestController

@RequestMapping(value = "/publisher", produces = MediaType.APPLICATION_JSON_VALUE)
public class PublisherBusinessEmployeeController
{
	private static Logger logger = LoggerFactory
			.getLogger(PublisherBusinessEmployeeController.class);

	@Autowired
	PublisherBusinessEmployeeService publisherBusinessEmployeeService;

	@Autowired
	ApplicationContext applicationContext;

	/**
	 * To create the employee user a publisher business and publisher
	 * 
	 * @param publBusinessEmployee
	 * @return To get the message like "Employee has been successfully created"
	 */
	@PostMapping(path = "/employee/create", consumes = "application/json")
	public ResponseBean employeeCreate(
			@RequestBody PublisherBusinessEmployee publBusinessEmployee)
	{
		ResponseBean respBean = this.applicationContext
				.getBean(ResponseBean.class);
		try
		{
			if (publBusinessEmployee.getCreatedTime() == null)
			{
				Date dt = new Date();
				publBusinessEmployee.setCreatedTime(dt);
				publBusinessEmployee.setUpdatedTime(dt);
			}

			this.publisherBusinessEmployeeService.save(publBusinessEmployee);

			respBean.setCode(StatusConstants.SUCCESS_CODE);
			respBean.setMessage("Employee has been successfully created");
			respBean.setResult(null);
		} catch (Exception e)
		{

			respBean.setCode(StatusConstants.INTERNAL_SERVER_ERROR);
			respBean.setMessage(StatusConstants.INTERNAL_SERVER_ERROR_MESSAGE);
			respBean.setResult(null);
			e.printStackTrace();
		}
		return respBean;
	}
	/**
	 * To update the employee based on employee object
	 * 
	 * @param publBusinessEmployee
	 * @return To get the message like "Employee has been successfully updated"
	 */
	@PutMapping(path = "/employee/update", consumes = "application/json")
	public ResponseBean updateEmployee(
			@RequestBody PublisherBusinessEmployee publBusinessEmployee)
	{
		ResponseBean respBean = this.applicationContext
				.getBean(ResponseBean.class);

		try
		{

			if (publBusinessEmployee.getUpdatedTime() == null)
			{
				publBusinessEmployee.setUpdatedTime(new java.util.Date());
			}

			String report = this.publisherBusinessEmployeeService
					.update(publBusinessEmployee);

			if (report.equals("INVALID"))
			{
				respBean.setCode(StatusConstants.NO_CONTENT);
				respBean.setMessage(
						"Invalid Details ! |   Employee has not been  updated");
				respBean.setResult(null);

			} else
			{
				respBean.setCode(StatusConstants.SUCCESS_CODE);
				respBean.setMessage("Employee has been successfully updated");
				respBean.setResult(null);
			}

		}

		catch (Exception e)
		{
			respBean.setCode(StatusConstants.INTERNAL_SERVER_ERROR);
			respBean.setMessage(StatusConstants.INTERNAL_SERVER_ERROR_MESSAGE);
			respBean.setResult(null);
			e.printStackTrace();
		}

		return respBean;
	}

	/**
	 * To get list of all employees based on publisherId and BusinessId
	 * otherwise get all employees
	 * 
	 * @param publisherId,
	 *            BusinessId
	 * @return To get list of all employees based on publisherId and BusinessId
	 */
	@PostMapping(path = "/employee/list", consumes = "application/json", produces = "application/json")
	public ResponseBean getList(@RequestBody PublisherBusinessEmployee pBE)
	{
		ResponseBean respBean = this.applicationContext
				.getBean(ResponseBean.class);
		try
		{
			respBean.setCode(StatusConstants.SUCCESS_CODE);
			respBean.setMessage(StatusConstants.SUCCESS_MESSAGE);
			respBean.setResult(
					this.publisherBusinessEmployeeService.employeeList(pBE));
		}

		catch (Exception e)
		{
			respBean.setCode(StatusConstants.INTERNAL_SERVER_ERROR);
			respBean.setMessage(StatusConstants.INTERNAL_SERVER_ERROR_MESSAGE);
			respBean.setResult(null);
			e.printStackTrace();
		}

		return respBean;
	}

	/**
	 * To Create the Appointment
	 * 
	 * @param appointBean
	 * @return To get the message like "Appointment has been create
	 *         successfully"
	 */
	@PostMapping(path = "/employee/saveappointment", consumes = "application/json")
	public ResponseBean setAppointment(@RequestBody AppointmentBean appointBean)
	{
		ResponseBean respBean = this.applicationContext
				.getBean(ResponseBean.class);

		try
		{

			String report = this.publisherBusinessEmployeeService
					.createAppointment(appointBean);

			List<Integer> details = this.publisherBusinessEmployeeService
					.getAppointmentDetails(appointBean);

			if (report.equals("INVALID_DETAILS"))
			{
				respBean.setCode(StatusConstants.NO_CONTENT);
				respBean.setMessage(StatusConstants.INVALID_DETAILS);
				respBean.setResult(null);

			}

			else if (report.equals("HOLI_DAY"))
			{
				respBean.setCode(StatusConstants.NO_CONTENT);
				respBean.setMessage(
						"Sorry today ts holiday! Please select another date ");
				respBean.setResult(null);
			}

			else if (report.equals("NotWorkingHours"))
			{
				int s = details.get(details.size() - 2);
				int e = details.get(details.size() - 1);

				int sHour = s / 60;
				int sminute = s % 60;

				int eHour = e / 60;
				int eminute = e % 60;

				respBean.setCode(StatusConstants.NO_CONTENT);
				respBean.setMessage("Sorry, Plz select business hours between "
						+ sHour + ":" + sminute + "  To " + eHour + ":"
						+ eminute);
				respBean.setResult(null);

			}

			else if (report.equals("TimesMismatched"))
			{
				respBean.setCode(StatusConstants.NO_CONTENT);
				respBean.setMessage(
						"Appointment end time must be greater than start time !");
				respBean.setResult(null);
			}

			else if (report.equals("LIST_EMPTY"))
			{
				respBean.setCode(StatusConstants.NO_CONTENT);
				respBean.setMessage("Employee not available!");
				respBean.setResult(null);
			}

			else if (report.equals("NO_SERVICE"))
			{
				respBean.setCode(StatusConstants.NO_CONTENT);
				respBean.setMessage("Employee service not started !");
				respBean.setResult(null);
			}

			else if (report.equals("NO_FIT"))
			{
				respBean.setCode(StatusConstants.NO_CONTENT);
				respBean.setMessage(
						"Time is  not sufficient for the service, you chosen !");
				respBean.setResult(null);
			}

			else if (report.equals("BREAK"))
			{
				respBean.setCode(StatusConstants.NO_CONTENT);
				respBean.setMessage("Break Time !");
				respBean.setResult(null);
			}

			else if (report.equals("ALL_BOOKED"))
			{
				respBean.setCode(StatusConstants.NO_CONTENT);
				respBean.setMessage(
						"All services booked ! Please select another time ! ");
				respBean.setResult(null);
			}

			else
			{
				respBean.setCode(StatusConstants.SUCCESS_CODE);
				respBean.setMessage("Appointment has been create successfully");
				respBean.setResult(null);
			}
			details.clear();
		}

		catch (Exception e)
		{
			respBean.setCode(StatusConstants.INTERNAL_SERVER_ERROR);
			respBean.setMessage(StatusConstants.INTERNAL_SERVER_ERROR_MESSAGE);
			respBean.setResult(null);
			e.printStackTrace();
		}

		return respBean;
	}

	/**
	 * To create Appointment Calender details
	 * 
	 * @param calender
	 * @return
	 */
	@PostMapping(path = "/employee/calender", consumes = "application/json")
	public ResponseBean calenderCreate(
			@RequestBody AppointmentCalendar calender)
	{
		ResponseBean respBean = this.applicationContext
				.getBean(ResponseBean.class);
		try
		{

			this.publisherBusinessEmployeeService.save(calender);

			respBean.setCode(StatusConstants.SUCCESS_CODE);
			respBean.setMessage(StatusConstants.SUCCESS_MESSAGE);
			respBean.setResult(null);

		} catch (Exception e)
		{

			respBean.setCode(StatusConstants.INTERNAL_SERVER_ERROR);
			respBean.setMessage(StatusConstants.INTERNAL_SERVER_ERROR_MESSAGE);
			respBean.setResult(null);
			e.printStackTrace();
		}
		return respBean;
	}

	/**
	 * To delete the employee based on employeeId
	 * 
	 * @param employeeId
	 * @return To get message like "Employee has been successfully deleted"
	 */
	@DeleteMapping(value = "/employee/delete/{id}", produces = "application/json")
	public ResponseBean removeEmployeeById(
			@PathVariable("id") String employeeId)
	{
		ResponseBean respBean = this.applicationContext
				.getBean(ResponseBean.class);
		try
		{

			String report = this.publisherBusinessEmployeeService
					.deleteEmployeeById(employeeId);

			if (report.equals("INVALID"))
			{
				respBean.setCode(StatusConstants.NOT_VALID);
				respBean.setMessage(StatusConstants.INVALID_DETAILS);
				respBean.setResult(null);
			}

			else if (report.equals("UNSUCCESS"))
			{

				respBean.setCode(StatusConstants.NO_CONTENT);
				respBean.setMessage("NO Employee Found");
				respBean.setResult(null);

			}

			else
			{
				respBean.setCode(StatusConstants.SUCCESS_CODE);
				respBean.setMessage("Employee has been successfully deleted");
				respBean.setResult(null);
			}

		} catch (Exception e)
		{
			respBean.setCode(StatusConstants.INTERNAL_SERVER_ERROR);
			respBean.setMessage(StatusConstants.INTERNAL_SERVER_ERROR_MESSAGE);
			respBean.setResult(null);
			e.printStackTrace();
		}
		return respBean;
	}

	/**
	 * To list the all appointments in a particular date and PublisherId
	 * 
	 * @param AppointmentCalendar
	 *            object
	 * 
	 * @return To list the all appointments in a particular date and PublisherId
	 */
	@PostMapping(path = "/getappointment/", produces = "application/json", consumes = "application/json")
	public ResponseBean listOfEmpAppointmentsByDate(
			@RequestBody AppointmentBean appCla)
	{
		ResponseBean respBean = this.applicationContext
				.getBean(ResponseBean.class);
		try
		{

			Object report = this.publisherBusinessEmployeeService
					.listOfEmpAppointmentsByDate(appCla);

			if (report.equals("Holiday"))
			{
				respBean.setCode(StatusConstants.NOT_VALID);
				respBean.setMessage("Today  is holiday");
				respBean.setResult(null);
			}

			else if (report.equals("INVALID_BUSINESS"))
			{
				respBean.setCode(StatusConstants.NOT_VALID);
				respBean.setMessage("Invalid Business !");
				respBean.setResult(null);
			}

			else if (report.equals("No_Employee_Found"))
			{
				respBean.setCode(StatusConstants.NOT_VALID);
				respBean.setMessage("No Employee Found !");
				respBean.setResult(null);
			}

			else if (report.equals("DETAILS_REQUIRED"))
			{
				respBean.setCode(StatusConstants.NO_CONTENT);
				respBean.setMessage("Details Required !");
				respBean.setResult(null);
			}

			else
			{
				respBean.setCode(StatusConstants.SUCCESS_CODE);
				respBean.setMessage(StatusConstants.SUCCESS_MESSAGE);
				respBean.setResult(report);
			}

		} catch (Exception e)
		{

			respBean.setCode(StatusConstants.INTERNAL_SERVER_ERROR);
			respBean.setMessage(StatusConstants.INTERNAL_SERVER_ERROR_MESSAGE);
			respBean.setResult(null);
			e.printStackTrace();
		}
		return respBean;
	}

	/**
	 * To Create Employee PTO Detalis
	 * 
	 * @param pto
	 * @return
	 */
	@PostMapping(path = "/employee/savepto/", produces = "application/json", consumes = "application/json")
	public ResponseBean savePto(@RequestBody EmployeePto pto)
	{
		ResponseBean respBean = this.applicationContext
				.getBean(ResponseBean.class);

		try
		{

			this.publisherBusinessEmployeeService.savePto(pto);
			respBean.setCode(StatusConstants.SUCCESS_CODE);
			respBean.setMessage("PTO details has been successfully created");
			respBean.setResult(null);

		} catch (Exception e)
		{
			respBean.setCode(StatusConstants.INTERNAL_SERVER_ERROR);
			respBean.setMessage(StatusConstants.INTERNAL_SERVER_ERROR_MESSAGE);
			respBean.setResult(null);
			e.printStackTrace();
		}

		return respBean;
	}

	/**
	 * To Add PTO Details To Particular Employee
	 * 
	 * @param pto
	 * @return
	 */
	@PostMapping(path = "/employee/addpto/", produces = "application/json", consumes = "application/json")
	public ResponseBean addPto(@RequestBody AddPto pto)
	{
		ResponseBean respBean = this.applicationContext
				.getBean(ResponseBean.class);

		try
		{

			String report = this.publisherBusinessEmployeeService.addPto(pto);

			if (report.equals("Success"))
			{
				respBean.setCode(StatusConstants.SUCCESS_CODE);
				respBean.setMessage("PTO details has been successfully added");
				respBean.setResult(null);
			} else if (report.equals("UnSuccess"))
			{
				respBean.setCode(StatusConstants.NO_CONTENT);
				respBean.setMessage("PTO details has not been  added");
				respBean.setResult(null);
			}

		} catch (Exception e)
		{
			respBean.setCode(StatusConstants.INTERNAL_SERVER_ERROR);
			respBean.setMessage(StatusConstants.INTERNAL_SERVER_ERROR_MESSAGE);
			respBean.setResult(null);
			e.printStackTrace();
		}

		return respBean;
	}

	/**
	 * 
	 * To Update PTO Detalis
	 * 
	 * @param updatePto
	 * @return
	 */
	@PutMapping(path = "/employee/updatepto", consumes = "application/json")
	public ResponseBean updatePto(@RequestBody EmployeePto updatePto)
	{
		ResponseBean respBean = this.applicationContext
				.getBean(ResponseBean.class);

		try
		{

			String report = this.publisherBusinessEmployeeService
					.updatePto(updatePto);

			if (report.equals("INVALID"))
			{
				respBean.setCode(StatusConstants.NO_CONTENT);
				respBean.setMessage(
						"Invalid Data ! | PTO Details  has not been  updated");
				respBean.setResult(null);

			} else
			{
				respBean.setCode(StatusConstants.SUCCESS_CODE);
				respBean.setMessage(
						"PTO Details  has been successfully updated");
				respBean.setResult(null);
			}

		}

		catch (Exception e)
		{
			respBean.setCode(StatusConstants.INTERNAL_SERVER_ERROR);
			respBean.setMessage(StatusConstants.INTERNAL_SERVER_ERROR_MESSAGE);
			respBean.setResult(null);
			e.printStackTrace();
		}

		return respBean;
	}

	/**
	 * 
	 * To Remove PTO Details to the Particular Employee
	 * 
	 * @param removePto
	 * @return
	 */
	@PostMapping(path = "/employee/removepto/", produces = "application/json", consumes = "application/json")
	public ResponseBean removePto(@RequestBody EmployeePto removePto)
	{
		ResponseBean respBean = this.applicationContext
				.getBean(ResponseBean.class);

		try
		{
			String report = this.publisherBusinessEmployeeService
					.removePto(removePto);

			if (report.equals("Success"))
			{
				respBean.setCode(StatusConstants.SUCCESS_CODE);
				respBean.setMessage(
						"PTO details has been successfully removed");
				respBean.setResult(null);
			}

			else if (report.equals("UnSuccess"))
			{
				respBean.setCode(StatusConstants.NO_CONTENT);
				respBean.setMessage("PTO details has not been remove!");
				respBean.setResult(null);
			}

		} catch (Exception e)
		{
			respBean.setCode(StatusConstants.INTERNAL_SERVER_ERROR);
			respBean.setMessage(StatusConstants.INTERNAL_SERVER_ERROR_MESSAGE);
			respBean.setResult(null);
			e.printStackTrace();
		}

		return respBean;
	}

	/**
	 * To List The PTO Details Based on the Particular EmpId
	 * 
	 * @param empId
	 * @return
	 */
	@GetMapping(path = "/employee/ptolist/{id}", produces = "application/json")
	public ResponseBean ptoList(@PathVariable("id") String empId)
	{
		ResponseBean respBean = this.applicationContext
				.getBean(ResponseBean.class);

		try
		{
			respBean.setCode(StatusConstants.SUCCESS_CODE);
			respBean.setMessage(StatusConstants.SUCCESS_MESSAGE);
			respBean.setResult(
					this.publisherBusinessEmployeeService.PtoList(empId));

		} catch (Exception e)
		{
			respBean.setCode(StatusConstants.INTERNAL_SERVER_ERROR);
			respBean.setMessage(StatusConstants.INTERNAL_SERVER_ERROR_MESSAGE);
			respBean.setResult(null);
			e.printStackTrace();
		}

		return respBean;
	}

	/**
	 * 
	 * List Of Services of Particular Employee Based On EmpId
	 * 
	 * @param empId
	 * @return
	 */
	@GetMapping(value = "/employee/servicelist/{id}", produces = "application/json")
	public ResponseBean publisherBusinessServiceListByEmpId(
			@PathVariable("id") String empId)
	{
		ResponseBean respBean = this.applicationContext
				.getBean(ResponseBean.class);

		try
		{
			respBean.setCode(StatusConstants.SUCCESS_CODE);
			respBean.setMessage(StatusConstants.SUCCESS_MESSAGE);
			respBean.setResult(this.publisherBusinessEmployeeService
					.publisherBusinessServiceListByEmpId(empId));
		}

		catch (Exception e)
		{
			respBean.setCode(StatusConstants.INTERNAL_SERVER_ERROR);
			respBean.setMessage(StatusConstants.INTERNAL_SERVER_ERROR_MESSAGE);
			respBean.setResult(null);
			e.printStackTrace();
		}
		return respBean;
	}

	@PutMapping(path = "/updateappointmentstatus", consumes = "application/json")
	public ResponseBean updateAppointmentStatus(
			@RequestBody AppointmentCalendar updateStatus)
	{
		ResponseBean respBean = this.applicationContext
				.getBean(ResponseBean.class);

		try
		{

			String report = this.publisherBusinessEmployeeService
					.updateStatus(updateStatus);

			if (report.equals("INVALID"))
			{
				respBean.setCode(StatusConstants.NO_CONTENT);
				respBean.setMessage(
						"Invalid Data ! | appointment status  has not been  updated");
				respBean.setResult(null);

			} else
			{
				respBean.setCode(StatusConstants.SUCCESS_CODE);
				respBean.setMessage(
						"Appointment status  has been successfully updated");
				respBean.setResult(null);
			}

		}

		catch (Exception e)
		{
			respBean.setCode(StatusConstants.INTERNAL_SERVER_ERROR);
			respBean.setMessage(StatusConstants.INTERNAL_SERVER_ERROR_MESSAGE);
			respBean.setResult(null);
			e.printStackTrace();
		}

		return respBean;
	}

	/**
	 * To list the all appointments in a particular date and PublisherId
	 * 
	 * @param AppointmentCalendar
	 *            object
	 * 
	 * @return To list the all appointments in a particular date and PublisherId
	 */
	@PostMapping(path = "/getappointmentconsumer/", produces = "application/json", consumes = "application/json")
	public ResponseBean listOfServicesBasedAppointmentByDate(
			@RequestBody AppointmentBean appCla)
	{
		ResponseBean respBean = this.applicationContext
				.getBean(ResponseBean.class);
		try
		{

			Object report = this.publisherBusinessEmployeeService
					.listOfServicesBasedAppointmentByDate(appCla);

			if (report.equals("Holiday"))
			{
				respBean.setCode(StatusConstants.NOT_VALID);
				respBean.setMessage("Today  is holiday");
				respBean.setResult(null);
			}

			else if (report.equals("INVALID_BUSINESS"))
			{
				respBean.setCode(StatusConstants.NOT_VALID);
				respBean.setMessage("Invalid Business !");
				respBean.setResult(null);
			}

			else if (report.equals("No_Employee_Found"))
			{
				respBean.setCode(StatusConstants.NOT_VALID);
				respBean.setMessage("No Employee Found !");
				respBean.setResult(null);
			}

			else if (report.equals("DETAILS_REQUIRED"))
			{
				respBean.setCode(StatusConstants.NO_CONTENT);
				respBean.setMessage("Details Required !");
				respBean.setResult(null);
			}

			else
			{
				respBean.setCode(StatusConstants.SUCCESS_CODE);
				respBean.setMessage(StatusConstants.SUCCESS_MESSAGE);
				respBean.setResult(report);
			}

		} catch (Exception e)
		{

			respBean.setCode(StatusConstants.INTERNAL_SERVER_ERROR);
			respBean.setMessage(StatusConstants.INTERNAL_SERVER_ERROR_MESSAGE);
			respBean.setResult(null);
			e.printStackTrace();
		}
		return respBean;
	}

	@GetMapping(path = "/consumerappoinmentlist/{id}", produces = "application/json")
	public ResponseBean consumerAppoinmentList(
			@PathVariable("id") String deviceId)
	{
		ResponseBean respBean = this.applicationContext
				.getBean(ResponseBean.class);

		try
		{

			List<AppointmentCalendar> app = this.publisherBusinessEmployeeService
					.consumerAppoinmentList(deviceId);

			if (app == null)
			{
				respBean.setCode(StatusConstants.NOT_VALID);
				respBean.setMessage(StatusConstants.INVALID_DETAILS);
				respBean.setResult(app);
			}

			else
			{
				respBean.setCode(StatusConstants.SUCCESS_CODE);
				respBean.setMessage("Consumer appoinment list");
				respBean.setResult(app);

			}

		} catch (Exception e)
		{
			respBean.setCode(StatusConstants.INTERNAL_SERVER_ERROR);
			respBean.setMessage(StatusConstants.INTERNAL_SERVER_ERROR_MESSAGE);
			respBean.setResult(null);
			e.printStackTrace();

		}
		return respBean;
	}

}
