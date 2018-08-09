package com.businessApp.service;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.TimeZone;
import java.util.TreeMap;

import org.bson.types.ObjectId;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.domain.Sort.Order;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.AggregationResults;
import org.springframework.data.mongodb.core.aggregation.MatchOperation;
import org.springframework.data.mongodb.core.aggregation.ProjectionOperation;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;

import com.businessApp.bean.AddPto;
import com.businessApp.bean.Appointment;
import com.businessApp.bean.AppointmentBean;
import com.businessApp.bean.ConsumerAppointmentBean;
import com.businessApp.bean.ConsumerHours;
import com.businessApp.bean.ConsumerServices;
import com.businessApp.bean.Employees;
import com.businessApp.bean.GetAppoinmentPublisherBean;
import com.businessApp.bean.GetAppointmentConsumerBean;
import com.businessApp.bean.Hours;
import com.businessApp.bean.Services;
import com.businessApp.model.AppointmentCalendar;
import com.businessApp.model.BusinessBreakConfig;
import com.businessApp.model.BusinessHours;
import com.businessApp.model.ConsumerBusinessDetails;
import com.businessApp.model.EmployeePto;
import com.businessApp.model.Pto;
import com.businessApp.model.PublisherBusiness;
import com.businessApp.model.PublisherBusinessEmployee;
import com.businessApp.repositories.PublisherAppointmentCalenderRepository;
import com.businessApp.repositories.PublisherBusinessEmpPtoRepository;
import com.businessApp.repositories.PublisherBusinessEmployeeRepository;
import com.mongodb.BasicDBObject;
import com.mongodb.WriteResult;

@Service
public class PublisherBusinessEmployeeService
{
	private static Logger logger = LoggerFactory
			.getLogger(PublisherBusinessEmployeeService.class);

	List<Integer> details = new ArrayList<>();

	@Autowired
	PublisherBusinessEmployeeRepository pubBusEmpRepo;
	@Autowired
	PublisherAppointmentCalenderRepository pubAppCal;

	@Autowired
	PublisherBusinessEmpPtoRepository pubPto;

	@Autowired
	GetAppointmentConsumerBean getAppBean;

	@Autowired
	PublisherService pubService;

	@Autowired
	UserService userService;

	@Autowired
	MongoTemplate mongoTemplate;

	/**
	 * To Create the Publisher Employee to the given employee Object
	 * 
	 * @param publBusinessEmployee
	 */
	public void save(PublisherBusinessEmployee publBusinessEmployee)
	{
		this.pubBusEmpRepo.save(publBusinessEmployee);

	}

	/**
	 * @param PublisherBusinessEmployee
	 * @return List of Employees based on BussId and PublisherId
	 * @throws Exception
	 */
	public List<PublisherBusinessEmployee> employeeList(
			PublisherBusinessEmployee pBE) throws Exception

	{

		Query query = new Query(Criteria.where("publisherId")
				.is(pBE.getPublisherId()).andOperator(
						Criteria.where("businessId").is(pBE.getBusinessId())));

		List<PublisherBusinessEmployee> pBEmpList = this.mongoTemplate
				.find(query, PublisherBusinessEmployee.class);

		List<PublisherBusinessEmployee> employeeList = new ArrayList<>();

		PublisherBusinessEmployee tmp;

		if ((pBEmpList.size()) > 0 && (pBEmpList != null))
		{

			for (PublisherBusinessEmployee pBEmp : pBEmpList)
			{
				tmp = addServiceNaming(pBEmp);
				employeeList.add(tmp);
			}
		}

		return employeeList;

	}

	private PublisherBusinessEmployee addServiceNaming(
			PublisherBusinessEmployee pBEmp)
	{

		Query qr = new Query();
		qr.addCriteria(Criteria.where("publisherId").is(pBEmp.getPublisherId())
				.andOperator(Criteria.where("id").is(pBEmp.getBusinessId())));
		PublisherBusiness pbBus = this.mongoTemplate.findOne(qr,
				PublisherBusiness.class);

		PublisherBusiness tmp;

		if (pbBus != null)
		{

			tmp = this.pubService.addNaming(pbBus);

			if (tmp.getServiceCategory().size() > 0)
			{

				for (int i = 0; i < pBEmp.getServiceCategory().size(); i++)
				{

					for (int p = 0; p < tmp.getServiceCategory().size(); p++)

					{

						if (pBEmp.getServiceCategory().get(i).getId().equals(
								tmp.getServiceCategory().get(p).getId()))
						{

							// Assign Service Category Name

							pBEmp.getServiceCategory().get(i).setName(
									tmp.getServiceCategory().get(p).getName());

							// Assign Service Category Description

							pBEmp.getServiceCategory().get(i)
									.setDescription(tmp.getServiceCategory()
											.get(p).getDescription());

							for (int j = 0; j < pBEmp.getServiceCategory()
									.get(i).getService().size(); j++)
							{

								for (int q = 0; q < tmp.getServiceCategory()
										.get(p).getService().size(); q++)
								{

									if (tmp.getServiceCategory().get(p)
											.getService().get(q).getId()
											.equals(pBEmp.getServiceCategory()
													.get(i).getService().get(j)
													.getId()))
									{
										pBEmp.getServiceCategory().get(i)
												.getService().get(j)
												.setName(tmp
														.getServiceCategory()
														.get(p).getService()
														.get(q).getName());

										pBEmp.getServiceCategory().get(i)
												.getService().get(j)
												.setDescription(tmp
														.getServiceCategory()
														.get(p).getService()
														.get(q)
														.getDescription());

										pBEmp.getServiceCategory().get(i)
												.getService().get(j)
												.setDuration(tmp
														.getServiceCategory()
														.get(p).getService()
														.get(q).getDuration());

									}

								}

							}
						}

					}

				}

			}

		}

		return pBEmp;
	}

	/**
	 * To Update The PublisherBusinessEmployee
	 * 
	 * @param PublisherBusinessEmployee
	 * @return
	 * @throws Exception
	 */
	public String update(PublisherBusinessEmployee publBusinessEmployee)
			throws Exception
	{
		return this.pubBusEmpRepo.updateEmployee(publBusinessEmployee);
	}

	/**
	 * To delete the particular Employee based on employeeId
	 * 
	 * @param employeeId
	 * @return
	 */
	public String deleteEmployeeById(String employeeId)
	{

		if (employeeId != null)
		{
			Query query = new Query();
			query.addCriteria(Criteria.where("id").is(employeeId));
			PublisherBusinessEmployee deletedEmp = this.mongoTemplate
					.findAndRemove(query, PublisherBusinessEmployee.class);

			if (deletedEmp != null)
			{
				return "SUCCESS";
			}

			else
			{
				return "UNSUCCESS";
			}

		} else
		{
			return "INVALID";
		}

	}

	/**
	 * To save AppointmentCalendar entries
	 * 
	 * @param AppointmentCalendar
	 */
	public void save(AppointmentCalendar calender)
	{
		this.pubAppCal.save(calender);

	}

	/**
	 * 
	 * To Add PTO Details to Particular Employee
	 * 
	 * @param PublisherBusinessEmployee
	 */
	public void savePto(EmployeePto pto)
	{
		this.pubPto.save(pto);
	}

	public String addPto(AddPto pto)
	{
		String report = "UnSuccess";

		Query query = new Query();
		query.addCriteria(Criteria.where("id").is(pto.getId()));

		EmployeePto ptoData = this.mongoTemplate.findOne(query,
				EmployeePto.class);

		if (ptoData != null)
		{
			report = addPtoToEmployee(pto);
		}

		return report;
	}

	public String updatePto(EmployeePto updatePto) throws Exception
	{
		return this.pubBusEmpRepo.updateEmployeePto(updatePto);
	}

	private String addPtoToEmployee(AddPto addPto)
	{

		String s;
		Query query = new Query();
		query.addCriteria(Criteria.where("id").is(addPto.getId()));
		Update update = new Update();
		update.addToSet("pto", addPto.getPto());
		EmployeePto add = this.mongoTemplate.findAndModify(query, update,
				EmployeePto.class);

		if (add != null)
		{
			s = "Success";
		} else
		{
			s = "UnSuccess";
		}

		return s;

	}

	public Object listOfServicesBasedAppointmentByDate(AppointmentBean appCla)
			throws ParseException
	{

		if ((appCla.getPublisherId() != null)
				&& (appCla.getBusinessId() != null)
				&& (appCla.getStartScheduledTime() != null))
		{

			List<PublisherBusinessEmployee> busEempList, finalEmpList;

			List<Integer> details = getAppointmentDetails(appCla);

			PublisherBusiness pbData = getBusinessDetails(
					appCla.getBusinessId(), appCla.getPublisherId());

			if (pbData != null)
			{

				if (pbData.getBusinessHours()
						.get(this.details.get(3).toString()).getHoliday() == 0)
				{

					busEempList = getEmpDetailsToPerformParticularService(
							appCla, details);

					if ((busEempList != null) && (busEempList.size() > 0))
					{

						List<Date> date = toConvertDateForPTO(appCla, details);

						finalEmpList = checkPtoDetails(busEempList, date,
								appCla);

						if ((finalEmpList != null) && (finalEmpList.size() > 0))
						{

							GetAppointmentConsumerBean getApps = getServiceBasedAppointmentDetails(
									pbData, appCla, details, finalEmpList);

							return getApps;

						} else
						{
							return "No_Employee_Found";

						}

					}

					else
					{
						return "No_Employee_Found";

					}

				}

				else
				{
					return "Holiday";
				}

			}

			else
			{
				return "INVALID_BUSINESS";
			}

		}

		else
		{
			return "DETAILS_REQUIRED";

		}
	}

	// public Object listOfServicesBasedAppointmentByDate(AppointmentBean
	// appCla)
	// {
	//
	// if ((appCla.getPublisherId() != null)
	// && (appCla.getBusinessId() != null)
	// && (appCla.getStartScheduledTime() != null))
	// {
	// List<PublisherBusinessEmployee> busEempList, finalPTOList;
	//
	// List<Integer> details = getAppointmentDetails(appCla);
	//
	// PublisherBusiness pbData = getBusinessDetails(appCla);
	//
	// if (pbData != null)
	// {
	//
	// if (pbData.getBusinessHours()
	// .get(this.details.get(3).toString()).getHoliday() == 0)
	// {
	//
	// busEempList = employeeCount(appCla.getBusinessId(),
	// appCla.getPublisherId(), details);
	//
	// BusinessBreakConfig breakConfig = getBusinessBreakDetails(
	// pbData.getId());
	//
	// List<AppointmentCalendar> apponitments =
	// getServiceBasedAppoinmentsOfADay(
	// appCla, pbData);
	// if ((apponitments != null))
	// {
	//
	// List<AppointmentCalendar> splitList = splitAppointmentList(
	// apponitments);
	//
	// GetAppointmentConsumerBean getApps = getServiceBasedAppointmentDetails(
	// splitList, pbData, appCla, details);
	//
	// return getApps;
	//
	// } else
	// {
	// return "NO_APPOINTMENTS";
	// }
	//
	// }
	//
	// else
	// {
	// return "Holiday";
	// }
	//
	// }
	//
	// else
	// {
	// return "INVALID_BUSINESS";
	// }
	//
	// }
	//
	// else
	// {
	// return "DETAILS_REQUIRED";
	//
	// }
	// }

	private GetAppointmentConsumerBean getServiceBasedAppointmentDetails(
			PublisherBusiness pbData, AppointmentBean appCla,
			List<Integer> details, List<PublisherBusinessEmployee> empList)
			throws ParseException
	{
		Map<String, ConsumerAppointmentBean> map = new HashMap<>();
		ConsumerAppointmentBean appointments;

		GetAppointmentConsumerBean setBean = new GetAppointmentConsumerBean();

		int open = pbData.getBusinessHours().get(this.details.get(3).toString())
				.getOpen();

		int close = pbData.getBusinessHours()
				.get(this.details.get(3).toString()).getClose();

		getServiceName(pbData, appCla.getServiceId());

		Integer sHour;

		setBean.setBusinessId(pbData.getId());
		setBean.setBusinessStartTime(open);
		setBean.setBusinessEndTime(close);
		setBean.setDate(appCla.getStartScheduledTime());

		logger.info("EMP SIZE---> " + empList.size());

		for (int i = 0; i < empList.size(); i++)
		{
			logger.info(" EMP - " + i + "--->" + empList.get(i).getId()
					+ " AND " + empList.get(i).getFirstName());
		}

		for (Integer pbStart = open; pbStart <= close;)
		{

			appointments = new ConsumerAppointmentBean();

			appointments.setAppointmentStartTime(pbStart.toString());
			Integer et = pbStart + 40 - 1;
			appointments.setAppointmentEndTime(et.toString());

			appointments.setServiceId(appCla.getServiceId());

			appointments.setServiceName(
					getServiceName(pbData, appCla.getServiceId()));
			appointments.setEmpCount(empList.size());

			Integer availEmpCount = empAvailCheck(appointments, appCla,
					empList);

			appointments.setSlots(availEmpCount.toString());

			sHour = pbStart / 60;

			map.put(sHour.toString() + ":" + pbStart % 60, appointments);

			pbStart = pbStart + 40;

		}

		Map<String, ConsumerAppointmentBean> sortedMap = new TreeMap<>(map);

		setBean.setAppointments(sortedMap);

		return setBean;
	}

	private Integer empAvailCheck(ConsumerAppointmentBean appointments,
			AppointmentBean appCla, List<PublisherBusinessEmployee> empList)
			throws ParseException
	{
		Integer count = 0;

		GetAppoinmentPublisherBean empAppointments = (GetAppoinmentPublisherBean) listOfEmpAppointmentsByDate(
				appCla);

		for (int i = 0; i < empList.size(); i++)
		{

			String sEid = empList.get(i).getId();

			logger.info("EMP ID----> " + empList.get(i).getId());

			List<Appointment> singleEmpAppointments = new ArrayList<>();

			for (Integer sh = empAppointments
					.getBusinessStartTime(); sh <= empAppointments
							.getBusinessEndTime(); sh++)
			{
				empAppointments.getHours().get(sh).getEmployees();
				for (int j = 0; j < empAppointments.getHours().get(sh)
						.getEmployees().size(); j++)
				{
					if (empAppointments.getHours().get(sh).getEmployees().get(j)
							.getEmpId().equals(sEid))
					{

						singleEmpAppointments.addAll(empAppointments.getHours()
								.get(sh).getEmployees().get(j)
								.getAppointment());
					}
				}
			}

			// logger.info("NO. OF APPOINTMENTS OF " + sEid + " ARE----> "
			// + singleEmpAppointments.size());

			List<Integer> availTime = new ArrayList<>();

			for (int k = 0; k < singleEmpAppointments.size(); k++)
			{
				// logger.info("APPOINTMENT TYPE---> "
				// + singleEmpAppointments.get(k).getType() + "@@@@@ "
				// + singleEmpAppointments.get(k).getAppointmentStartTime()
				// + " "
				// + singleEmpAppointments.get(k).getAppointmentEndTime());
				if (singleEmpAppointments.get(k).getType().equals("NoBooking"))
				{
					int temp1 = singleEmpAppointments.get(k)
							.getAppointmentStartTime();
					while (temp1 <= singleEmpAppointments.get(k)
							.getAppointmentEndTime())
					{
						availTime.add(temp1);

						temp1++;
					}
				}
			}

			List<Integer> aptTime = new ArrayList<>();

			int temp2 = Integer
					.parseInt(appointments.getAppointmentStartTime());
			int aptEnd = Integer.parseInt(appointments.getAppointmentEndTime());

			while (temp2 <= aptEnd)
			{
				aptTime.add(temp2);
				temp2++;
			}

			logger.info("" + availTime.size());
			aptTime.removeAll(availTime);

			logger.info("" + aptTime.size());
			if (aptTime.size() == 0)
			{
				count = count + 1;
			} else
			{
				count = count + 0;
			}

			aptTime.clear();
			availTime.clear();
		}

		return count;
	}

	private List<PublisherBusinessEmployee> getEmpDetailsToPerformParticularService(
			AppointmentBean appCla, List<Integer> details)
	{

		// List<String> empDetails = new ArrayList<>();

		ObjectId serviceId = new ObjectId(appCla.getServiceId());

		Query query = new Query();
		query.addCriteria(Criteria.where("businessId")
				.is(appCla.getBusinessId()).andOperator(Criteria
						.where("serviceCategory.service.id").is(serviceId)));
		List<PublisherBusinessEmployee> empData = this.mongoTemplate.find(query,
				PublisherBusinessEmployee.class);

		// List<PublisherBusinessEmployee> employeeList = new ArrayList<>();

		// PublisherBusinessEmployee tmp;
		// String serviceName = null;
		//
		// if ((empData.size()) > 0 && (empData != null))
		// {
		//
		// for (PublisherBusinessEmployee pBEmp : empData)
		// {
		// tmp = addServiceNaming(pBEmp);
		// employeeList.add(tmp);
		// }
		//
		// for (ServiceCategory serCat : employeeList.get(0)
		// .getServiceCategory())
		// {
		// for (ServiceType serv : serCat.getService())
		// {
		// if (serv.getId().equals(appCla.getServiceId()))
		// {
		// serviceName = serv.getName();
		//
		// }
		// }
		// }
		// }
		//
		// int count = employeeList.size();
		//
		// empDetails.add(0, Integer.toString(count));
		// empDetails.add(1, serviceName);
		//
		// return empDetails;

		return empData;

	}

	private void toFillServiceNullWithEmptyObjects(
			GetAppointmentConsumerBean finalObject, List<String> empData)
	{

		Map<String, List<Appointment>> appMap = null;

		Appointment aapp = null;
		List<Appointment> appointment = null;

		Map<Integer, ConsumerHours> sortedMap = new TreeMap<>(
				finalObject.getHours());

		for (int hour : sortedMap.keySet())
		{
			ConsumerHours hr = sortedMap.get(hour);

			int startTime = hour * 60;
			int endTime = startTime + 59;

			for (int j = 0; j < hr.getServices().size(); j++)
			{
				appointment = new ArrayList<>();

				if (hr.getServices().get(j).getAppointment() != null)
				{

					if (hr.getServices().get(j).getAppointment().isEmpty())
					{
						aapp = new Appointment();

						aapp.setId("");
						aapp.setServiceId("");

						aapp.setType("NoBooking");
						aapp.setAppointmentStartTime(startTime);
						aapp.setAppointmentEndTime(endTime);
						aapp.setColour("#FFFFFF");
						aapp.setServiceId(
								hr.getServices().get(j).getServiceId());
						aapp.setStatus(-1);
						aapp.setSlots(0);
						aapp.setEmpCount(Integer.parseInt(empData.get(0)));
						appointment.add(aapp);

						hr.getServices().get(j).setAppointment(appointment);
					} else
					{

						List<Integer> hourList = new ArrayList<>();
						for (int p = startTime; p <= endTime; p++)
						{
							hourList.add(p);
						}

						List<Integer> appList = new ArrayList<>();
						for (int i = 0; i < hr.getServices().get(j)
								.getAppointment().size(); i++)
						{

							int min = hr.getServices().get(j).getAppointment()
									.get(i).getAppointmentStartTime();
							int max = hr.getServices().get(j).getAppointment()
									.get(i).getAppointmentEndTime();

							for (int q = hr.getServices().get(j)
									.getAppointment().get(i)
									.getAppointmentStartTime(); q <= hr
											.getServices().get(j)
											.getAppointment().get(i)
											.getAppointmentEndTime(); q++)
							{
								appList.add(q);
							}

							if (hr.getServices().get(j).getAppointment()
									.size() == 1)
							{

								int startCh = hr.getServices().get(j)
										.getAppointment().get(i)
										.getAppointmentStartTime() - startTime;
								int endCh = endTime - hr.getServices().get(j)
										.getAppointment().get(i)
										.getAppointmentEndTime();

								if (startCh > 0)
								{
									aapp = new Appointment();
									aapp.setId("");
									aapp.setServiceId("");

									aapp.setType("NoBooking");
									aapp.setAppointmentStartTime(startTime);
									aapp.setAppointmentEndTime(hr.getServices()
											.get(j).getAppointment().get(i)
											.getAppointmentStartTime() - 1);
									aapp.setColour("#FFFFFF");
									aapp.setServiceId(hr.getServices().get(j)
											.getServiceId());
									aapp.setSlots(0);
									aapp.setEmpCount(
											Integer.parseInt(empData.get(0)));
									aapp.setStatus(-1);

									sortedMap.get(hour).getServices().get(j)
											.getAppointment().add(0, aapp);

									if (endCh > 0)
									{
										aapp = new Appointment();
										aapp.setId("");
										aapp.setServiceId("");

										aapp.setType("NoBooking");
										aapp.setAppointmentStartTime(
												hr.getServices().get(j)
														.getAppointment().get(1)
														.getAppointmentEndTime()
														+ 1);
										aapp.setAppointmentEndTime(endTime);
										aapp.setColour("#FFFFFF");
										aapp.setServiceId(hr.getServices()
												.get(j).getServiceId());
										aapp.setStatus(-1);
										aapp.setSlots(0);
										aapp.setEmpCount(Integer
												.parseInt(empData.get(0)));
										sortedMap.get(hour).getServices().get(j)
												.getAppointment().add(2, aapp);

									}

								}

								else if (endCh > 0)
								{

									aapp = new Appointment();
									aapp.setId("");
									aapp.setServiceId("");

									aapp.setType("NoBooking");
									aapp.setAppointmentStartTime(
											hr.getServices().get(j)
													.getAppointment().get(i)
													.getAppointmentEndTime()
													+ 1);
									aapp.setAppointmentEndTime(endTime);
									aapp.setColour("#FFFFFF");
									aapp.setServiceId(hr.getServices().get(j)
											.getServiceId());
									aapp.setStatus(-1);
									aapp.setSlots(0);
									aapp.setEmpCount(
											Integer.parseInt(empData.get(0)));
									sortedMap.get(hour).getServices().get(j)
											.getAppointment().add(1, aapp);

								}

							}

						}

					}

				}

				else
				{

					aapp = new Appointment();
					aapp.setId("");
					aapp.setServiceId("");

					aapp.setType("NoBooking");
					aapp.setAppointmentStartTime(startTime);
					aapp.setAppointmentEndTime(endTime);
					aapp.setColour("#FFFFFF");
					aapp.setServiceId(hr.getServices().get(j).getServiceId());
					aapp.setStatus(-1);
					aapp.setSlots(0);
					aapp.setEmpCount(Integer.parseInt(empData.get(0)));
					appointment.add(aapp);
					hr.getServices().get(j).setAppointment(appointment);

				}
			}

		}

	}

	private GetAppointmentConsumerBean toSetServiceBasedAppoinments(
			GetAppointmentConsumerBean setBean,
			List<AppointmentCalendar> appSplitList, List<String> empData)
	{

		try
		{

			Map<Integer, ConsumerHours> sortedMap = new TreeMap<>(
					setBean.getHours());

			Map<Integer, Map<String, List<Appointment>>> apps = serviceTimeBasedAppointment(
					appSplitList, setBean.getHours(), empData);

			for (int hour : setBean.getHours().keySet())
			{
				ConsumerHours hr = setBean.getHours().get(hour);

				for (ConsumerServices serv : hr.getServices())
				{

					String key = String.valueOf(hour) + ":"
							+ serv.getServiceId();
					if (apps.containsKey(hour))
					{

						Map<String, List<Appointment>> apList = apps.get(hour);

						if (apList.containsKey(key))
						{
							// logger.info("Key ---" + key + "apList --- "
							// + apList.get(key));
							serv.setAppointment(apList.get(key));

						}
					} else
					{
						// logger.info("In else block is "+emps.hashCode());
						serv.setAppointment(null);
					}
				}
			}

			// logger.info("After sortedMap " + setBean.getHours());
		} catch (Exception e)
		{
			e.printStackTrace();
		}
		return setBean;
	}

	private Map<Integer, Map<String, List<Appointment>>> serviceTimeBasedAppointment(
			List<AppointmentCalendar> apponitments,
			HashMap<Integer, ConsumerHours> hoursMap, List<String> empData)
	{

		Map<Integer, Map<String, List<Appointment>>> finalMap = new HashMap<>();

		Map<String, List<Appointment>> appMap = null;

		List<Appointment> appointment = null;

		Appointment aapp = null;

		for (Entry<Integer, ConsumerHours> hour : hoursMap.entrySet())
		{
			appMap = new HashMap<>();

			for (int serv = 0; serv < hour.getValue().getServices()
					.size(); serv++)
			{

				String key = hour.getKey().toString() + ":" + hour.getValue()
						.getServices().get(serv).getServiceId();
				appointment = new ArrayList<>();

				for (int app = 0; app < apponitments.size(); app++)
				{

					int start = apponitments.get(app).getStart() / 60;

					int end = apponitments.get(app).getEnd() / 60;

					if ((start == hour.getKey()) && (end == hour.getKey()))
					{

						if (hour.getValue().getServices().get(serv)
								.getServiceId()
								.equals(apponitments.get(app).getServiceId()))
						{
							int startTime = 0, endTime = 0;
							aapp = new Appointment();

							Date sDate = apponitments.get(app)
									.getStartScheduledTime();
							Calendar calendar = Calendar.getInstance();
							calendar.setTime(sDate);

							int hr = calendar.get(Calendar.HOUR_OF_DAY);
							int minute = calendar.get(Calendar.MINUTE);

							startTime = hr * 60 + minute;

							Date eDate = apponitments.get(app)
									.getEndScheduledTime();
							Calendar calendar2 = Calendar.getInstance();
							calendar2.setTime(eDate);

							int hr2 = calendar2.get(Calendar.HOUR_OF_DAY);
							int minute2 = calendar2.get(Calendar.MINUTE);

							endTime = hr2 * 60 + minute2;

							aapp.setId(apponitments.get(app).getId());
							aapp.setServiceId(
									apponitments.get(app).getServiceId());

							aapp.setType("Booked");
							aapp.setConsumerName(
									apponitments.get(app).getName());
							aapp.setConsumerPhoneNo(
									apponitments.get(app).getPhone());
							aapp.setAppointmentStartTime(startTime);
							aapp.setAppointmentEndTime(endTime);
							aapp.setColour(apponitments.get(app).getColour());
							aapp.setServiceName(
									apponitments.get(app).getServiceName());
							aapp.setStatus(apponitments.get(app).getStatus());
							aapp.setEmpId(
									apponitments.get(app).getEmployeeId());
							aapp.setSlots(0);
							aapp.setEmpCount(Integer.parseInt(empData.get(0)));
							appointment.add(aapp);

						}
						appMap.put(key, appointment);
					}

				}

				if (!appMap.isEmpty())
				{

					finalMap.put(hour.getKey(), appMap);
				}

			}

		}

		Map<Integer, Map<String, List<Appointment>>> sortMap = new TreeMap<>(
				finalMap);

		removeNulls(sortMap);

		for (Entry<Integer, Map<String, List<Appointment>>> entry : sortMap
				.entrySet())
		{

			for (Entry<String, List<Appointment>> entry2 : entry.getValue()
					.entrySet())
			{

				for (Appointment p : entry2.getValue())
				{

					// System.out.println(entry2.getKey() + " | Id -- " +
					// p.getId()
					// + " ConsumerName :---" + p.getConsumerName()
					// + " Start Time --" + p.getAppointmentStartTime()
					// + " End Time --" + p.getAppointmentEndTime());
				}

			}
		}
		return sortMap;

	}

	private List<AppointmentCalendar> getServiceBasedAppoinmentsOfADay(
			AppointmentBean appCla, PublisherBusiness pbData)
	{

		Query query = new Query();

		query.addCriteria(
				Criteria.where("publisherId").is(appCla.getPublisherId())
						.and("serviceId").is(appCla.getServiceId())
						.andOperator(Criteria.where("startScheduledTime")
								.gte(appCla.getStartScheduledTime())
								.and("endScheduledTime")
								.lt(appCla.getEndScheduledTime())));
		query.with(new Sort(new Order(Direction.ASC, "startScheduledTime")));

		List<AppointmentCalendar> appList = this.mongoTemplate.find(query,
				AppointmentCalendar.class);

		for (int ap = 0; ap < appList.size(); ap++)
		{
			for (int i = 0; i < pbData.getServiceCategory().size(); i++)
			{

				for (int j = 0; j < pbData.getServiceCategory().get(i)
						.getService().size(); j++)
				{

					if (appList.get(ap).getServiceId()
							.equals(pbData.getServiceCategory().get(i)
									.getService().get(j).getId()))
					{

						appList.get(ap).setColour(
								pbData.getServiceCategory().get(i).getColour());

						appList.get(ap)
								.setServiceName(pbData.getServiceCategory()
										.get(i).getService().get(j).getName());

					}
				}

			}

		}

		return appList;

	}

	/**
	 * 
	 * To Listing all Appointments details based on Date and publisherId
	 * 
	 * @param AppointmentCalendar
	 * @return List of Appointments
	 * @throws ParseException
	 */
	public Object listOfEmpAppointmentsByDate(AppointmentBean appCla)
			throws ParseException
	{

		if ((appCla.getPublisherId() != null)
				&& (appCla.getBusinessId() != null)
				&& (appCla.getStartScheduledTime() != null))
		{

			List<PublisherBusinessEmployee> busEempList, finalPTOList;

			List<Integer> details = getAppointmentDetails(appCla);

			// PublisherBusiness pbData = getBusinessDetails(appCla);

			PublisherBusiness pbData = getBusinessDetails(
					appCla.getBusinessId(), appCla.getPublisherId());

			if (pbData != null)
			{

				if (pbData.getBusinessHours()
						.get(this.details.get(3).toString()).getHoliday() == 0)
				{

					busEempList = employeeCount(appCla.getBusinessId(),
							appCla.getPublisherId(), details);

					if ((busEempList != null) && (busEempList.size() > 0))
					{

						List<Date> date = toConvertDateForPTO(appCla, details);

						finalPTOList = checkPtoDetails(busEempList, date,
								appCla);

						if ((finalPTOList != null) && (finalPTOList.size() > 0))
						{

							BusinessBreakConfig breakConfig = getBusinessBreakDetails(
									pbData.getId());

							List<AppointmentCalendar> apponitments = getAppoinmentsOfADay(
									appCla, pbData);
							if ((apponitments != null))
							{

								if (breakConfig != null)
								{

									if (breakConfig.getBreakStatus() == 1)
									{
										List<AppointmentCalendar> empBreaks = empBreakConfiguration(
												pbData, finalPTOList, details,
												appCla, breakConfig);
										if (!(empBreaks.isEmpty()))
										{
											apponitments.addAll(empBreaks);
										}
									}
								}

								List<AppointmentCalendar> dummyObject = toFillEmpGapBusinessHourToEmpStartHourWithEmptyObjects(
										finalPTOList, pbData, appCla);

								apponitments.addAll(dummyObject);

								List<AppointmentCalendar> splitList = splitAppointmentList(
										apponitments);

								Collections.sort(finalPTOList,
										PublisherBusinessEmployee.PublisherBusinessEmployeePriority);

								GetAppoinmentPublisherBean getApps = getEmpAppointmentDetails(
										splitList, pbData, appCla, details,
										finalPTOList);

								return getApps;

							} else
							{
								return "NO_APPOINTMENTS";
							}
						} else
						{
							return "No_Employee_Found";

						}

					}

					else
					{
						return "No_Employee_Found";

					}

				}

				else
				{
					return "Holiday";
				}

			}

			else
			{
				return "INVALID_BUSINESS";
			}

		}

		else
		{
			return "DETAILS_REQUIRED";

		}

	}

	private BusinessBreakConfig getBusinessBreakDetails(String id)
	{
		Query query = new Query();
		query.addCriteria(Criteria.where("bId").is(id));
		return this.mongoTemplate.findOne(query, BusinessBreakConfig.class);

	}

	private List<AppointmentCalendar> empBreakConfiguration(
			PublisherBusiness pbData, List<PublisherBusinessEmployee> empList,
			List<Integer> details, AppointmentBean appCla,
			BusinessBreakConfig breakConfig)
	{

		List<AppointmentCalendar> temp1 = new ArrayList<>();

		AppointmentCalendar aapp, aapp1;

		List<AppointmentCalendar> appointment = null;

		String tmp = details.get(3).toString();

		String brTemp = breakConfig.getFrequency();

		String frq[] = brTemp.split(":");

		int frequency = Integer.parseInt(frq[0]) * 60
				+ Integer.parseInt(frq[1]);

		// int frequency = Integer.parseInt(breakConfig.getFrequency());

		int interval = breakConfig.getInterval();

		int empCount = empList.size();

		int breakCase = interval * empCount;

		if (breakCase <= 60)
		{
			appointment = new ArrayList<>();

			int breakStart;

			int breakEnd;

			int breakStart1;

			int breakEnd1;

			for (int i = 0; i < empList.size(); i++)
			{

				Integer wd = getAppointmentDetails(appCla).get(3);

				HashMap<String, BusinessHours> eopen = empList.get(i)
						.getEmployeeHours();

				int open = eopen.get(wd.toString()).getOpen();
				int close = eopen.get(wd.toString()).getClose();

				breakStart = open + frequency;

				aapp = new AppointmentCalendar();

				breakEnd = breakStart + interval - 1;

				Date sDate = appCla.getStartScheduledTime();
				Date eDate = appCla.getStartScheduledTime();

				Calendar calendar = Calendar.getInstance();
				calendar.setTime(sDate);

				Calendar calendar2 = Calendar.getInstance();
				calendar2.setTime(eDate);

				calendar.add(Calendar.MINUTE, breakStart);
				Date dateStart = calendar.getTime();

				calendar2.add(Calendar.MINUTE, breakEnd);
				Date dateEnd = calendar2.getTime();

				aapp.setId("");
				aapp.setServiceId("");
				aapp.setBusinessId(pbData.getId());
				aapp.setColour("#FFC7BF");
				aapp.setPublisherId(pbData.getPublisherId());
				aapp.setEmployeeId(empList.get(i).getId());
				aapp.setStatus(-2);
				aapp.setStart(breakStart);
				aapp.setEnd(breakEnd);
				aapp.setStartScheduledTime(dateStart);
				aapp.setEndScheduledTime((dateEnd));

				if (breakStart >= open && breakEnd <= close)
				{
					temp1.add(aapp);

				}

				// second break
				aapp1 = new AppointmentCalendar();

				breakStart1 = breakEnd + frequency + 1;
				breakEnd1 = breakStart1 + interval - 1;

				Date sDate1 = appCla.getStartScheduledTime();
				Date eDate1 = appCla.getStartScheduledTime();

				Calendar calendar3 = Calendar.getInstance();
				calendar3.setTime(sDate1);

				Calendar calendar4 = Calendar.getInstance();
				calendar4.setTime(eDate1);

				calendar3.add(Calendar.MINUTE, breakStart1);
				Date dateStart1 = calendar3.getTime();

				calendar4.add(Calendar.MINUTE, breakEnd1);
				Date dateEnd1 = calendar4.getTime();

				aapp1.setId("");
				aapp1.setServiceId("");
				aapp1.setBusinessId(pbData.getId());
				aapp1.setColour("#FFC7BF");
				aapp1.setPublisherId(pbData.getPublisherId());
				aapp1.setEmployeeId(empList.get(i).getId());
				aapp1.setStatus(-2);
				aapp1.setStart(breakStart1);
				aapp1.setEnd(breakEnd1);
				aapp1.setStartScheduledTime(dateStart1);
				aapp1.setEndScheduledTime(dateEnd1);

				if (breakStart1 >= open && breakEnd1 <= close)
				{
					temp1.add(aapp1);

				}

			}

			appointment.addAll(temp1);

			temp1.clear();

		}

		else
		{

			appointment = new ArrayList<>();

			int breakStart;

			int breakEnd = 0;

			int breakStart1;

			int breakEnd1;

			for (int i = 0; i < empList.size();)
			{

				Integer wd = getAppointmentDetails(appCla).get(3);

				HashMap<String, BusinessHours> eopen = empList.get(i)
						.getEmployeeHours();

				int open = eopen.get(wd.toString()).getOpen();
				int close = eopen.get(wd.toString()).getClose();

				breakStart = open + frequency;

				aapp = new AppointmentCalendar();

				breakEnd = breakStart + interval - 1;

				Date sDate = appCla.getStartScheduledTime();
				Date eDate = appCla.getStartScheduledTime();

				Calendar calendar = Calendar.getInstance();
				calendar.setTime(sDate);

				Calendar calendar2 = Calendar.getInstance();
				calendar2.setTime(eDate);

				calendar.add(Calendar.MINUTE, breakStart);
				Date dateStart = calendar.getTime();

				calendar2.add(Calendar.MINUTE, breakEnd);
				Date dateEnd = calendar2.getTime();

				aapp.setId("");
				aapp.setServiceId("");
				aapp.setBusinessId(pbData.getId());
				aapp.setColour("#FFC7BF");
				aapp.setPublisherId(pbData.getPublisherId());
				aapp.setEmployeeId(empList.get(i).getId());
				aapp.setStatus(-2);
				aapp.setStart(breakStart);
				aapp.setEnd(breakEnd);
				aapp.setStartScheduledTime(dateStart);
				aapp.setEndScheduledTime((dateEnd));

				if (breakStart >= open && breakEnd <= close)
				{
					temp1.add(aapp);

				}

				aapp1 = new AppointmentCalendar();

				breakStart1 = breakEnd + frequency + 1;
				breakEnd1 = breakStart1 + interval - 1;

				Date sDate1 = appCla.getStartScheduledTime();
				Date eDate1 = appCla.getStartScheduledTime();

				Calendar calendar3 = Calendar.getInstance();
				calendar3.setTime(sDate1);

				Calendar calendar4 = Calendar.getInstance();
				calendar4.setTime(eDate1);

				calendar3.add(Calendar.MINUTE, breakStart1);
				Date dateStart1 = calendar3.getTime();

				calendar4.add(Calendar.MINUTE, breakEnd1);
				Date dateEnd1 = calendar4.getTime();

				aapp1.setId("");
				aapp1.setServiceId("");
				aapp1.setBusinessId(pbData.getId());
				aapp1.setColour("#FFC7BF");
				aapp1.setPublisherId(pbData.getPublisherId());
				aapp1.setEmployeeId(empList.get(i).getId());
				aapp1.setStatus(-2);
				aapp1.setStart(breakStart1);
				aapp1.setEnd(breakEnd1);
				aapp1.setStartScheduledTime(dateStart1);
				aapp1.setEndScheduledTime(dateEnd1);

				if (breakStart1 >= open && breakEnd1 <= close)
				{
					temp1.add(aapp1);

				}

				i = i + 2;

			}

			for (int i = 1; i < empList.size();)
			{

				Integer wd = getAppointmentDetails(appCla).get(3);

				HashMap<String, BusinessHours> eopen = empList.get(i)
						.getEmployeeHours();

				int open = eopen.get(wd.toString()).getOpen();
				int close = eopen.get(wd.toString()).getClose();

				breakStart = open + frequency;

				aapp = new AppointmentCalendar();

				breakEnd = breakStart + interval - 1;

				Date sDate = appCla.getStartScheduledTime();
				Date eDate = appCla.getStartScheduledTime();

				Calendar calendar = Calendar.getInstance();
				calendar.setTime(sDate);

				Calendar calendar2 = Calendar.getInstance();
				calendar2.setTime(eDate);

				calendar.add(Calendar.MINUTE, breakStart);
				Date dateStart = calendar.getTime();

				calendar2.add(Calendar.MINUTE, breakEnd);
				Date dateEnd = calendar2.getTime();

				aapp.setId("");
				aapp.setServiceId("");
				aapp.setBusinessId(pbData.getId());
				aapp.setColour("#FFC7BF");
				aapp.setPublisherId(pbData.getPublisherId());
				aapp.setEmployeeId(empList.get(i).getId());
				aapp.setStatus(-2);
				aapp.setStart(breakStart);
				aapp.setEnd(breakEnd);
				aapp.setStartScheduledTime(dateStart);
				aapp.setEndScheduledTime((dateEnd));

				if (breakStart >= open && breakEnd <= close)
				{
					temp1.add(aapp);

				}

				aapp1 = new AppointmentCalendar();

				breakStart1 = breakEnd + frequency + 1;
				breakEnd1 = breakStart1 + interval - 1;

				Date sDate1 = appCla.getStartScheduledTime();
				Date eDate1 = appCla.getStartScheduledTime();

				Calendar calendar3 = Calendar.getInstance();
				calendar3.setTime(sDate1);

				Calendar calendar4 = Calendar.getInstance();
				calendar4.setTime(eDate1);

				calendar3.add(Calendar.MINUTE, breakStart1);
				Date dateStart1 = calendar3.getTime();

				calendar4.add(Calendar.MINUTE, breakEnd1);
				Date dateEnd1 = calendar4.getTime();

				aapp1.setId("");
				aapp1.setServiceId("");
				aapp1.setBusinessId(pbData.getId());
				aapp1.setColour("#FFC7BF");
				aapp1.setPublisherId(pbData.getPublisherId());
				aapp1.setEmployeeId(empList.get(i).getId());
				aapp1.setStatus(-2);
				aapp1.setStart(breakStart1);
				aapp1.setEnd(breakEnd1);
				aapp1.setStartScheduledTime(dateStart1);
				aapp1.setEndScheduledTime(dateEnd1);

				if (breakStart1 >= open && breakEnd1 <= close)
				{
					temp1.add(aapp1);

				}

				i = i + 2;

			}

			appointment.addAll(temp1);

			temp1.clear();

		}

		return appointment;

	}

	// private List<AppointmentCalendar> empBreakConfiguration(
	// PublisherBusiness pbData, List<PublisherBusinessEmployee> empList,
	// List<Integer> details, AppointmentBean appCla,
	// BusinessBreakConfig breakConfig)
	// {
	//
	// List<AppointmentCalendar> temp1 = new ArrayList<>();
	//
	// AppointmentCalendar aapp, aapp1;
	//
	// List<AppointmentCalendar> appointment = null;
	//
	// String tmp = details.get(3).toString();
	//
	// String brTemp = breakConfig.getFrequency();
	//
	// String frq[] = brTemp.split(":");
	//
	// int frequency = Integer.parseInt(frq[0]) * 60
	// + Integer.parseInt(frq[1]);
	//
	// // int frequency = Integer.parseInt(breakConfig.getFrequency());
	//
	// int interval = breakConfig.getInterval();
	//
	// int empCount = empList.size();
	//
	// int breakCase = interval * empCount;
	//
	// // if (empList.size() > 1) // Sorting The Emp List
	// // {
	// // for (int i = 0; i < empList.size(); i++)
	// // {
	// //
	// // for (int j = empList.size() - 1; j > i; j--)
	// // {
	// // if (empList.get(i).getEmployeeHours().get(tmp)
	// // .getOpen() > empList.get(j).getEmployeeHours()
	// // .get(tmp).getOpen())
	// // {
	// //
	// // PublisherBusinessEmployee temp = empList.get(i);
	// // empList.set(i, empList.get(j));
	// // empList.set(j, temp);
	// //
	// // }
	// //
	// // }
	// //
	// // }
	// //
	// // }
	//
	// if (breakCase <= 60)
	// {
	// appointment = new ArrayList<>();
	//
	//
	//
	// int breakStart = empList.get(0).getEmployeeHours().get(tmp)
	// .getOpen() + frequency;
	//
	// int breakEnd;
	//
	// int breakStart1;
	//
	// int breakEnd1;
	//
	// for (int i = 0; i < empList.size(); i++)
	// {
	//
	// aapp = new AppointmentCalendar();
	//
	// breakEnd = breakStart + interval - 1;
	//
	// Date sDate = appCla.getStartScheduledTime();
	// Date eDate = appCla.getStartScheduledTime();
	//
	// // logger.info("breakStart -------" + breakStart);
	// // logger.info("breakEnd----------" + breakEnd);
	//
	// Calendar calendar = Calendar.getInstance();
	// calendar.setTime(sDate);
	//
	// Calendar calendar2 = Calendar.getInstance();
	// calendar2.setTime(eDate);
	//
	// calendar.add(Calendar.MINUTE, breakStart);
	// Date dateStart = calendar.getTime();
	//
	// // logger.info("dateStart ---" + dateStart);
	// //
	// calendar2.add(Calendar.MINUTE, breakEnd);
	// Date dateEnd = calendar2.getTime();
	//
	// // logger.info("dateEnd ---" + dateEnd);
	//
	// aapp.setId("");
	// aapp.setServiceId("");
	// aapp.setBusinessId(pbData.getId());
	// aapp.setColour("#FFC7BF");
	// aapp.setPublisherId(pbData.getPublisherId());
	// aapp.setEmployeeId(empList.get(i).getId());
	// aapp.setStatus(-2);
	// aapp.setStart(breakStart);
	// aapp.setEnd(breakEnd);
	// aapp.setStartScheduledTime(dateStart);
	// aapp.setEndScheduledTime((dateEnd));
	// temp1.add(aapp);
	//
	// // second break
	// aapp1 = new AppointmentCalendar();
	//
	// breakStart1 = breakEnd + frequency + 1;
	// breakEnd1 = breakStart1 + interval - 1;
	//
	// Date sDate1 = appCla.getStartScheduledTime();
	// Date eDate1 = appCla.getStartScheduledTime();
	//
	// // logger.info("breakStart -------" + breakStart1);
	// // logger.info("breakEnd----------" + breakEnd1);
	//
	// Calendar calendar3 = Calendar.getInstance();
	// calendar3.setTime(sDate1);
	//
	// Calendar calendar4 = Calendar.getInstance();
	// calendar4.setTime(eDate1);
	//
	// calendar3.add(Calendar.MINUTE, breakStart1);
	// Date dateStart1 = calendar3.getTime();
	//
	// // logger.info("dateStart ---" + dateStart1);
	// //
	// calendar4.add(Calendar.MINUTE, breakEnd1);
	// Date dateEnd1 = calendar4.getTime();
	//
	// // logger.info("dateEnd ---" + dateEnd1);
	//
	// aapp1.setId("");
	// aapp1.setServiceId("");
	// aapp1.setBusinessId(pbData.getId());
	// aapp1.setColour("#FFC7BF");
	// aapp1.setPublisherId(pbData.getPublisherId());
	// aapp1.setEmployeeId(empList.get(i).getId());
	// aapp1.setStatus(-2);
	// aapp1.setStart(breakStart1);
	// aapp1.setEnd(breakEnd1);
	// aapp1.setStartScheduledTime(dateStart1);
	// aapp1.setEndScheduledTime(dateEnd1);
	// temp1.add(aapp1);
	//
	// breakStart = breakEnd + 1;
	//
	// }
	//
	// appointment.addAll(temp1);
	//
	// temp1.clear();
	//
	// }
	//
	// else
	// {
	//
	// appointment = new ArrayList<>();
	// // int breakStart = empList.get(0).getEmployeeHours().get(tmp)
	// // .getOpen() + frequency * 60;
	//
	// int breakStart = empList.get(0).getEmployeeHours().get(tmp)
	// .getOpen() + frequency;
	//
	// int breakEnd = 0;
	//
	// int breakStart1;
	//
	// int breakEnd1;
	//
	// for (int i = 0; i < empList.size();)
	// {
	//
	// aapp = new AppointmentCalendar();
	//
	// breakEnd = breakStart + interval - 1;
	//
	// Date sDate = appCla.getStartScheduledTime();
	// Date eDate = appCla.getStartScheduledTime();
	//
	// // logger.info("breakStart -------" + breakStart);
	// // logger.info("breakEnd----------" + breakEnd);
	//
	// Calendar calendar = Calendar.getInstance();
	// calendar.setTime(sDate);
	//
	// Calendar calendar2 = Calendar.getInstance();
	// calendar2.setTime(eDate);
	//
	// calendar.add(Calendar.MINUTE, breakStart);
	// Date dateStart = calendar.getTime();
	//
	// // logger.info("dateStart ---" + dateStart);
	// //
	// calendar2.add(Calendar.MINUTE, breakEnd);
	// Date dateEnd = calendar2.getTime();
	//
	// // logger.info("dateEnd ---" + dateEnd);
	//
	// aapp.setId("");
	// aapp.setServiceId("");
	// aapp.setBusinessId(pbData.getId());
	// aapp.setColour("#FFC7BF");
	// aapp.setPublisherId(pbData.getPublisherId());
	// aapp.setEmployeeId(empList.get(i).getId());
	// aapp.setStatus(-2);
	// aapp.setStart(breakStart);
	// aapp.setEnd(breakEnd);
	// aapp.setStartScheduledTime(dateStart);
	// aapp.setEndScheduledTime((dateEnd));
	// temp1.add(aapp);
	//
	// // logger.info("breakStart -------" + breakStart);
	// // logger.info("breakEnd----------" + breakEnd);
	//
	// // second break
	//
	// aapp1 = new AppointmentCalendar();
	//
	// breakStart1 = breakEnd + frequency + 1;
	// breakEnd1 = breakStart1 + interval - 1;
	//
	// Date sDate1 = appCla.getStartScheduledTime();
	// Date eDate1 = appCla.getStartScheduledTime();
	//
	// // logger.info("breakStart -------" + breakStart1);
	// // logger.info("breakEnd----------" + breakEnd1);
	//
	// Calendar calendar3 = Calendar.getInstance();
	// calendar3.setTime(sDate1);
	//
	// Calendar calendar4 = Calendar.getInstance();
	// calendar4.setTime(eDate1);
	//
	// calendar3.add(Calendar.MINUTE, breakStart1);
	// Date dateStart1 = calendar3.getTime();
	//
	// // logger.info("dateStart ---" + dateStart1);
	// //
	// calendar4.add(Calendar.MINUTE, breakEnd1);
	// Date dateEnd1 = calendar4.getTime();
	//
	// // logger.info("dateEnd ---" + dateEnd1);
	//
	// aapp1.setId("");
	// aapp1.setServiceId("");
	// aapp1.setBusinessId(pbData.getId());
	// aapp1.setColour("#FFC7BF");
	// aapp1.setPublisherId(pbData.getPublisherId());
	// aapp1.setEmployeeId(empList.get(i).getId());
	// aapp1.setStatus(-2);
	// aapp1.setStart(breakStart1);
	// aapp1.setEnd(breakEnd1);
	// aapp1.setStartScheduledTime(dateStart1);
	// aapp1.setEndScheduledTime(dateEnd1);
	// temp1.add(aapp1);
	//
	// i = i + 2;
	//
	// }
	//
	// breakStart = breakEnd + 1;
	//
	// for (int i = 1; i < empList.size();)
	// {
	// aapp = new AppointmentCalendar();
	//
	// breakEnd = breakStart + interval - 1;
	//
	// Date sDate = appCla.getStartScheduledTime();
	// Date eDate = appCla.getStartScheduledTime();
	//
	// // logger.info("breakStart -------" + breakStart);
	// // logger.info("breakEnd----------" + breakEnd);
	//
	// Calendar calendar = Calendar.getInstance();
	// calendar.setTime(sDate);
	//
	// Calendar calendar2 = Calendar.getInstance();
	// calendar2.setTime(eDate);
	//
	// calendar.add(Calendar.MINUTE, breakStart);
	// Date dateStart = calendar.getTime();
	//
	// // logger.info("dateStart ---" + dateStart);
	// //
	// calendar2.add(Calendar.MINUTE, breakEnd);
	// Date dateEnd = calendar2.getTime();
	//
	// // logger.info("dateEnd ---" + dateEnd);
	//
	// aapp.setId("");
	// aapp.setServiceId("");
	// aapp.setBusinessId(pbData.getId());
	// aapp.setColour("#FFC7BF");
	// aapp.setPublisherId(pbData.getPublisherId());
	// aapp.setEmployeeId(empList.get(i).getId());
	// aapp.setStatus(-2);
	// aapp.setStart(breakStart);
	// aapp.setEnd(breakEnd);
	// aapp.setStartScheduledTime(dateStart);
	// aapp.setEndScheduledTime((dateEnd));
	// temp1.add(aapp);
	//
	// // logger.info("breakStart -------" + breakStart);
	// // logger.info("breakEnd----------" + breakEnd);
	//
	// // second break
	//
	// aapp1 = new AppointmentCalendar();
	//
	// breakStart1 = breakEnd + frequency + 1;
	// breakEnd1 = breakStart1 + interval - 1;
	//
	// Date sDate1 = appCla.getStartScheduledTime();
	// Date eDate1 = appCla.getStartScheduledTime();
	//
	// // logger.info("breakStart -------" + breakStart1);
	// // logger.info("breakEnd----------" + breakEnd1);
	//
	// Calendar calendar3 = Calendar.getInstance();
	// calendar3.setTime(sDate1);
	//
	// Calendar calendar4 = Calendar.getInstance();
	// calendar4.setTime(eDate1);
	//
	// calendar3.add(Calendar.MINUTE, breakStart1);
	// Date dateStart1 = calendar3.getTime();
	//
	// // logger.info("dateStart ---" + dateStart1);
	// //
	// calendar4.add(Calendar.MINUTE, breakEnd1);
	// Date dateEnd1 = calendar4.getTime();
	//
	// // logger.info("dateEnd ---" + dateEnd1);
	//
	// aapp1.setId("");
	// aapp1.setServiceId("");
	// aapp1.setBusinessId(pbData.getId());
	// aapp1.setColour("#FFC7BF");
	// aapp1.setPublisherId(pbData.getPublisherId());
	// aapp1.setEmployeeId(empList.get(i).getId());
	// aapp1.setStatus(-2);
	// aapp1.setStart(breakStart1);
	// aapp1.setEnd(breakEnd1);
	// aapp1.setStartScheduledTime(dateStart1);
	// aapp1.setEndScheduledTime(dateEnd1);
	// temp1.add(aapp1);
	//
	// i = i + 2;
	//
	// }
	//
	// appointment.addAll(temp1);
	//
	// temp1.clear();
	//
	// }
	//
	//
	// return appointment;
	//
	// }

	private List<AppointmentCalendar> toFillEmpGapBusinessHourToEmpStartHourWithEmptyObjects(
			List<PublisherBusinessEmployee> empList, PublisherBusiness pbData,
			AppointmentBean appCla) throws ParseException
	{
		AppointmentCalendar aapp;
		AppointmentCalendar aapp2;
		List<AppointmentCalendar> appointment = null;
		List<AppointmentCalendar> temp1 = new ArrayList<>();
		List<AppointmentCalendar> temp2 = new ArrayList<>();

		int open = pbData.getBusinessHours().get(this.details.get(3).toString())
				.getOpen();

		int close = pbData.getBusinessHours()
				.get(this.details.get(3).toString()).getClose();

		open = open - (open % 60);
		close = close + (60 - (close % 60));

		// logger.info("Business Start Time : " + open);
		// logger.info("Business End Time : " + close);
		appointment = new ArrayList<>();

		for (int i = 0; i < empList.size(); i++)
		{
			aapp2 = new AppointmentCalendar();
			aapp = new AppointmentCalendar();
			SimpleDateFormat sdf2 = new SimpleDateFormat(
					"yyyy-MM-dd'T'HH:mm:ss.SSSZ");

			sdf2.setTimeZone(TimeZone.getTimeZone("UTC"));

			if ((empList.get(i).getEmployeeHours()
					.get(this.details.get(3).toString()).getOpen() > open))

			{

				Date sDate = appCla.getStartScheduledTime();
				Date eDate = appCla.getStartScheduledTime();

				Calendar calendar = Calendar.getInstance();
				calendar.setTime(sDate);

				Calendar calendar2 = Calendar.getInstance();
				calendar2.setTime(eDate);

				// logger.info("Dates ---" + sDate);

				calendar.add(Calendar.MINUTE, open);
				Date dateStart = calendar.getTime();

				// logger.info("dateStart ---" + dateStart);

				calendar2.add(Calendar.MINUTE,
						empList.get(i).getEmployeeHours()
								.get(this.details.get(3).toString()).getOpen()
								- 1);
				Date dateEnd = calendar2.getTime();

				aapp.setId("");
				aapp.setServiceId("");
				aapp.setBusinessId(pbData.getId());
				aapp.setColour("#B4CAE9");
				aapp.setPublisherId(pbData.getPublisherId());
				aapp.setEmployeeId(empList.get(i).getId());
				aapp.setStatus(-3);
				aapp.setStart(open);
				aapp.setEnd(empList.get(i).getEmployeeHours()
						.get(this.details.get(3).toString()).getOpen() - 1);
				aapp.setStartScheduledTime(dateStart);
				aapp.setEndScheduledTime((dateEnd));
				temp1.add(aapp);

			}

			if (empList.get(i).getEmployeeHours()
					.get(this.details.get(3).toString()).getClose() < close)
			{

				Date sDate = appCla.getStartScheduledTime();
				Date eDate = appCla.getStartScheduledTime();

				Calendar calendar = Calendar.getInstance();
				calendar.setTime(sDate);

				Calendar calendar2 = Calendar.getInstance();
				calendar2.setTime(eDate);
				// logger.info("Dates ---" + sDate);

				calendar.add(Calendar.MINUTE,
						empList.get(i).getEmployeeHours()
								.get(this.details.get(3).toString()).getClose()
								+ 1);
				Date dateStart = calendar.getTime();

				// logger.info("dateStart ---" + dateStart);

				calendar2.add(Calendar.MINUTE, close - 1);
				Date dateEnd = calendar2.getTime();

				// logger.info("dateEnd ---" + dateEnd);

				aapp2.setId("");
				aapp2.setServiceId("");
				aapp2.setBusinessId(pbData.getId());
				aapp2.setColour("#B4CAE9");
				aapp2.setPublisherId(pbData.getPublisherId());
				aapp2.setEmployeeId(empList.get(i).getId());
				aapp2.setStatus(-3);
				aapp2.setStart(empList.get(i).getEmployeeHours()
						.get(this.details.get(3).toString()).getClose() + 1);
				aapp2.setEnd(close - 1);
				aapp2.setStartScheduledTime(dateStart);
				aapp2.setEndScheduledTime(dateEnd);

				temp2.add(aapp2);

				// logger.info("EMP Close Time -------" + empList.get(i)
				// .getEmployeeHours().get(this.details.get(3).toString())
				// .getClose());
				// logger.info("Business close -----" + close);
			}

		}

		appointment.addAll(temp1);
		appointment.addAll(temp2);

		temp1.clear();
		temp2.clear();

		// logger.info(
		// "Dummy Object --------------------------------------------");
		//
		// for (AppointmentCalendar dummy : appointment)
		// {
		// logger.info("Dummy Object ----" + dummy.getEmployeeId() + "------"
		// + dummy.getStart() + "-----" + dummy.getEnd());
		// logger.info("Dates ----" + dummy.getEmployeeId() + "------"
		// + dummy.getStartScheduledTime() + "-----"
		// + dummy.getEndScheduledTime());
		// }

		return appointment;

	}

	private GetAppoinmentPublisherBean getEmpAppointmentDetails(
			List<AppointmentCalendar> apponitments, PublisherBusiness pbData,
			AppointmentBean appCla, List<Integer> details,
			List<PublisherBusinessEmployee> busEempList)
	{
		HashMap<Integer, Hours> map = new HashMap<>();
		Hours hours;

		List<Employees> empList = new ArrayList<>();

		GetAppoinmentPublisherBean setBean = new GetAppoinmentPublisherBean();

		int open = pbData.getBusinessHours().get(this.details.get(3).toString())
				.getOpen();

		int close = pbData.getBusinessHours()
				.get(this.details.get(3).toString()).getClose();

		int sHour = open / 60;

		int eHour = close / 60;

		int hoursDiff = eHour - sHour;
		int hrr = sHour;
		int hStart = sHour;

		setBean.setBusinessId(pbData.getId());
		setBean.setBusinessStartTime(sHour);
		setBean.setBusinessEndTime(eHour);
		setBean.setNoOfemployee(busEempList.size());
		setBean.setDate(appCla.getStartScheduledTime());

		for (int hour = 0; hour <= hoursDiff; hour++)
		{

			hours = new Hours();
			hours.setTime(Integer.toString(hrr));

			empList = null;
			empList = new ArrayList<>();

			for (int emp = 0; emp < busEempList.size(); emp++)
			{
				Employees employee = new Employees();
				employee.setEmployeeName(busEempList.get(emp).getFirstName());
				employee.setEmpId(busEempList.get(emp).getId());
				empList.add(employee);
			}

			hours.setEmployees(empList);

			map.put(hStart, hours);
			hrr++;
			hStart++;
		}
		setBean.setHours(map);

		GetAppoinmentPublisherBean finalObject = toSetEmpAppoinments(setBean,
				apponitments);

		toFillEmpNullWithEmptyObjects(finalObject);

		return finalObject;
	}

	private void toFillEmpNullWithEmptyObjects(
			GetAppoinmentPublisherBean finalObject)
	{

		Appointment aapp = null;
		List<Appointment> appointment = null;

		Map<Integer, Hours> sortedMap = new TreeMap<>(finalObject.getHours());

		for (int hour : sortedMap.keySet())
		{
			Hours hr = sortedMap.get(hour);

			int startTime = hour * 60;
			int endTime = startTime + 59;

			for (int j = 0; j < hr.getEmployees().size(); j++)
			{
				appointment = new ArrayList<>();

				if (hr.getEmployees().get(j).getAppointment() != null)
				{

					if (hr.getEmployees().get(j).getAppointment().isEmpty())
					{
						aapp = new Appointment();

						aapp.setId("");
						aapp.setServiceId("");

						aapp.setType("NoBooking");
						aapp.setAppointmentStartTime(startTime);
						aapp.setAppointmentEndTime(endTime);
						aapp.setColour("#FFFFFF");
						aapp.setEmpId(hr.getEmployees().get(j).getEmpId());
						aapp.setStatus(-1);
						appointment.add(aapp);

						hr.getEmployees().get(j).setAppointment(appointment);
					} else
					{

						List<Integer> hourList = new ArrayList<>();
						for (int p = startTime; p <= endTime; p++)
						{
							hourList.add(p);
						}

						List<Integer> appList = new ArrayList<>();
						for (int i = 0; i < hr.getEmployees().get(j)
								.getAppointment().size(); i++)
						{

							if (hr.getEmployees().get(j).getAppointment().get(i)
									.getStatus() == -3)
							{
								hr.getEmployees().get(j).getAppointment().get(i)
										.setType("NoService");
							}

							if (hr.getEmployees().get(j).getAppointment().get(i)
									.getStatus() == -2)
							{
								hr.getEmployees().get(j).getAppointment().get(i)
										.setType("Break");
							}

							int min = hr.getEmployees().get(j).getAppointment()
									.get(i).getAppointmentStartTime();
							int max = hr.getEmployees().get(j).getAppointment()
									.get(i).getAppointmentEndTime();

							for (int q = hr.getEmployees().get(j)
									.getAppointment().get(i)
									.getAppointmentStartTime(); q <= hr
											.getEmployees().get(j)
											.getAppointment().get(i)
											.getAppointmentEndTime(); q++)
							{

								appList.add(q);
							}

						}

						hourList.removeAll(appList);

						if (hourList.size() > 0)
						{

							int lmin = hourList.get(0);
							int lmax;

							for (int k = 0; k < hourList.size(); k++)
							{

								if (k > 0) // k started at index 1
								{
									if (hourList.get(k)
											- hourList.get(k - 1) > 1)
									{
										lmax = hourList.get(k - 1);

										aapp = new Appointment();
										aapp.setId("");
										aapp.setServiceId("");

										aapp.setType("NoBooking");
										aapp.setAppointmentStartTime(lmin);
										aapp.setAppointmentEndTime(lmax);
										aapp.setColour("#FFFFFF");
										aapp.setEmpId(hr.getEmployees().get(j)
												.getEmpId());
										aapp.setStatus(-1);

										sortedMap.get(hour).getEmployees()
												.get(j).getAppointment()
												.add(0, aapp);

										lmin = hourList.get(k);
									}

									if (k == (hourList.size() - 1))
									{
										lmax = hourList.get(k);

										aapp = new Appointment();
										aapp.setId("");
										aapp.setServiceId("");

										aapp.setType("NoBooking");
										aapp.setAppointmentStartTime(lmin);
										aapp.setAppointmentEndTime(lmax);
										aapp.setColour("#FFFFFF");
										aapp.setEmpId(hr.getEmployees().get(j)
												.getEmpId());
										aapp.setStatus(-1);

										// getAppointment().size()

										sortedMap.get(hour).getEmployees()
												.get(j).getAppointment()
												.add(0, aapp);
									}
								}

							}

						}

					}

				}

				else
				{

					aapp = new Appointment();
					aapp.setId("");
					aapp.setServiceId("");

					aapp.setType("NoBooking");
					aapp.setAppointmentStartTime(startTime);
					aapp.setAppointmentEndTime(endTime);
					aapp.setColour("#FFFFFF");
					aapp.setEmpId(hr.getEmployees().get(j).getEmpId());
					aapp.setStatus(-1);
					appointment.add(aapp);
					hr.getEmployees().get(j).setAppointment(appointment);

				}
				Collections.sort(hr.getEmployees().get(j).getAppointment(),
						Appointment.AppointmentsSorting);
			}

		}

	}

	private GetAppoinmentPublisherBean toSetEmpAppoinments(
			GetAppoinmentPublisherBean setBean,
			List<AppointmentCalendar> apponitments)
	{
		try
		{

			Map<Integer, Hours> sortedMap = new TreeMap<>(setBean.getHours());

			Map<Integer, Map<String, List<Appointment>>> apps = empTimeBasedAppointment(
					apponitments, setBean.getHours());

			for (int hour : setBean.getHours().keySet())
			{
				Hours hr = setBean.getHours().get(hour);

				for (Employees emps : hr.getEmployees())
				{
					// logger.info("For emps code " + emps.hashCode()
					// + " In the Hour ---" + hour + " EMps " + emps);
					String key = String.valueOf(hour) + ":" + emps.getEmpId();
					if (apps.containsKey(hour))
					{

						Map<String, List<Appointment>> apList = apps.get(hour);

						if (apList.containsKey(key))
						{
							// logger.info("Key ---" + key + "apList --- "
							// + apList.get(key));
							emps.setAppointment(apList.get(key));

						}
					} else
					{
						// logger.info("In else block is "+emps.hashCode());
						emps.setAppointment(null);
					}
				}
			}

			// logger.info("After sortedMap " + setBean.getHours());
		} catch (Exception e)
		{
			e.printStackTrace();
		}
		return setBean;

	}

	private Map<Integer, Map<String, List<Appointment>>> empTimeBasedAppointment(
			List<AppointmentCalendar> apponitments,
			Map<Integer, Hours> hoursMap)
	{

		Map<Integer, Map<String, List<Appointment>>> finalMap = new HashMap<>();

		Map<String, List<Appointment>> appMap = null;

		List<Appointment> appointment = null;

		Appointment aapp = null;

		for (Entry<Integer, Hours> hour : hoursMap.entrySet())
		{
			appMap = new HashMap<>();

			for (int emp = 0; emp < hour.getValue().getEmployees()
					.size(); emp++)
			{

				String key = hour.getKey().toString() + ":"
						+ hour.getValue().getEmployees().get(emp).getEmpId();
				appointment = new ArrayList<>();

				for (int app = 0; app < apponitments.size(); app++)
				{

					int start = apponitments.get(app).getStart() / 60;

					int end = apponitments.get(app).getEnd() / 60;

					if ((start == hour.getKey()) && (end == hour.getKey()))
					{

						if (hour.getValue().getEmployees().get(emp).getEmpId()
								.equals(apponitments.get(app).getEmployeeId()))
						{
							int startTime = 0, endTime = 0;
							aapp = new Appointment();

							Date sDate = apponitments.get(app)
									.getStartScheduledTime();
							Calendar calendar = Calendar.getInstance();
							calendar.setTime(sDate);

							int hr = calendar.get(Calendar.HOUR_OF_DAY);
							int minute = calendar.get(Calendar.MINUTE);

							startTime = hr * 60 + minute;

							Date eDate = apponitments.get(app)
									.getEndScheduledTime();
							Calendar calendar2 = Calendar.getInstance();
							calendar2.setTime(eDate);

							int hr2 = calendar2.get(Calendar.HOUR_OF_DAY);
							int minute2 = calendar2.get(Calendar.MINUTE);

							endTime = hr2 * 60 + minute2;

							aapp.setId(apponitments.get(app).getId());
							aapp.setServiceId(
									apponitments.get(app).getServiceId());

							aapp.setType("Booked");
							aapp.setConsumerName(
									apponitments.get(app).getName());
							aapp.setConsumerPhoneNo(
									apponitments.get(app).getPhone());
							aapp.setAppointmentStartTime(startTime);
							aapp.setAppointmentEndTime(endTime);
							aapp.setColour(apponitments.get(app).getColour());
							aapp.setServiceName(
									apponitments.get(app).getServiceName());
							aapp.setStatus(apponitments.get(app).getStatus());
							aapp.setEmpId(
									apponitments.get(app).getEmployeeId());
							appointment.add(aapp);

						}
						appMap.put(key, appointment);
					}

				}

				if (!appMap.isEmpty())
				{

					finalMap.put(hour.getKey(), appMap);
				}

			}

		}

		Map<Integer, Map<String, List<Appointment>>> sortMap = new TreeMap<>(
				finalMap);

		removeNulls(sortMap);

		// for (Entry<Integer, Map<String, List<Appointment>>> entry : sortMap
		// .entrySet())
		// {
		//
		// System.out.print("Key" + entry.getKey());
		// for (Entry<String, List<Appointment>> entry2 : entry.getValue()
		// .entrySet())
		// {
		//
		// for (Appointment p : entry2.getValue())
		// {
		// System.out.println(entry2.getKey() + " | Id -- " + p.getId()
		// + " ConsumerName :---" + p.getConsumerName()
		// + " Start Time --" + p.getAppointmentStartTime()
		// + " End Time --" + p.getAppointmentEndTime());
		// }
		//
		// }
		//
		// }
		return sortMap;

	}

	private static <K, V> void removeNulls(
			Map<Integer, Map<String, List<Appointment>>> sortMap)
	{
		Iterator<Entry<Integer, Map<String, List<Appointment>>>> itr = sortMap
				.entrySet().iterator();

		while (itr.hasNext())
		{
			Entry<Integer, Map<String, List<Appointment>>> cur = itr.next();
			if (cur.getValue() == null)
			{
				itr.remove();
			}
		}

	}

	private List<AppointmentCalendar> splitAppointmentList(
			List<AppointmentCalendar> apponitments)
	{
		List<AppointmentCalendar> finalList = new ArrayList<>();

		AppointmentCalendar newApp = null;

		for (int i = 0; i < apponitments.size(); i++)
		{
			int startHour = apponitments.get(i).getStart() / 60;
			int endHour = apponitments.get(i).getEnd() / 60;

			// logger.info(
			// "StarHour : ==" + startHour + " End Hour ---" + endHour);

			int MAX = 59, startRemain = 0;

			int hoursDiff = endHour - startHour;

			if (hoursDiff > 0)
			{

				Date sDate = apponitments.get(i).getStartScheduledTime();

				Date eDate = apponitments.get(i).getEndScheduledTime();

				for (int j = 0; j < hoursDiff; j++)
				{
					Calendar calendar = Calendar.getInstance();
					calendar.setTime(sDate);

					int startMinute = calendar.get(Calendar.MINUTE);
					startRemain = MAX - startMinute;

					calendar.add(Calendar.MINUTE, startRemain);
					Date newDate = calendar.getTime();

					newApp = splitobjects(apponitments.get(i), sDate, newDate);
					finalList.add(newApp);

					// logger.info("Start Date ----" + sDate);
					// logger.info("New Date ----" + newDate);

					Calendar calendar2 = Calendar.getInstance();
					calendar2.setTime(newDate);
					calendar2.add(Calendar.MINUTE, 1);
					Date newDate2 = calendar2.getTime();
					sDate = newDate2;

					Calendar calendar3 = Calendar.getInstance();
					calendar3.setTime(newDate2);

					int hr2 = calendar3.get(Calendar.HOUR_OF_DAY);

					if (hr2 == endHour)
					{
						newApp = splitobjects(apponitments.get(i), sDate,
								eDate);
						finalList.add(newApp);
						// logger.info("Start Date ----" + sDate);
						// logger.info("end Date ----" + eDate);

					}

				}

			} else
			{
				finalList.add(apponitments.get(i));
			}

		}

		// for (AppointmentCalendar app : finalList)
		// {
		// logger.info("Appoit Start Date Time : ----"
		// + app.getStartScheduledTime());
		// logger.info(
		// "Appoit End Date Time : ----" + app.getEndScheduledTime());
		// }

		return finalList;
	}

	private AppointmentCalendar splitobjects(AppointmentCalendar appointment,
			Date sDate, Date newDate)
	{
		AppointmentCalendar app = new AppointmentCalendar();

		Calendar calendar = Calendar.getInstance();
		calendar.setTime(sDate);

		Calendar calendar2 = Calendar.getInstance();
		calendar2.setTime(newDate);

		int sHour = calendar.get(Calendar.HOUR_OF_DAY);
		int sminute = calendar.get(Calendar.MINUTE);

		int eHour = calendar2.get(Calendar.HOUR_OF_DAY);
		int eminute = calendar2.get(Calendar.MINUTE);

		int start = sHour * 60 + sminute;
		int end = eHour * 60 + eminute;

		app.setColour(appointment.getColour());
		app.setServiceName(appointment.getServiceName());
		app.setId(appointment.getId());
		app.setServiceId(appointment.getServiceId());
		app.setName(appointment.getName());
		app.setStatus(appointment.getStart());

		app.setBusinessId(appointment.getBusinessId());
		app.setPublisherId(appointment.getPublisherId());
		app.setEmployeeId(appointment.getEmployeeId());
		app.setPhone(appointment.getPhone());
		app.setEmail(appointment.getEmail());
		app.setStartScheduledTime(sDate);
		app.setEndScheduledTime(newDate);
		app.setStart(start);
		app.setEnd(end);
		app.setStatus(appointment.getStatus());

		return app;
	}

	private List<PublisherBusinessEmployee> employeeCount(String businessId,
			String publisherId, List<Integer> details)
	{

		List<PublisherBusinessEmployee> empList = new ArrayList<>();

		String tmp = details.get(3).toString();

		Query query = new Query();
		query.addCriteria(Criteria.where("businessId").is(businessId)
				.and("publisherId").is(publisherId));

		List<PublisherBusinessEmployee> empData = this.mongoTemplate.find(query,
				PublisherBusinessEmployee.class);

		if (empData != null)
		{
			for (PublisherBusinessEmployee emp : empData)
			{
				if (emp.getEmployeeHours().get(tmp).getHoliday() == 0)
				{
					empList.add(emp);
				}
			}
		}
		return empList;

	}

	private List<AppointmentCalendar> getAppoinmentsOfADay(
			AppointmentBean appCla, PublisherBusiness pbData)
	{

		Query query = new Query();

		query.addCriteria(
				Criteria.where("publisherId").is(appCla.getPublisherId())
						.andOperator(Criteria.where("startScheduledTime")
								.gte(appCla.getStartScheduledTime())
								.and("endScheduledTime")
								.lt(appCla.getEndScheduledTime())));
		query.with(new Sort(new Order(Direction.ASC, "startScheduledTime")));

		List<AppointmentCalendar> appList = this.mongoTemplate.find(query,
				AppointmentCalendar.class);

		for (int ap = 0; ap < appList.size(); ap++)
		{
			for (int i = 0; i < pbData.getServiceCategory().size(); i++)
			{

				for (int j = 0; j < pbData.getServiceCategory().get(i)
						.getService().size(); j++)
				{

					if (appList.get(ap).getServiceId()
							.equals(pbData.getServiceCategory().get(i)
									.getService().get(j).getId()))
					{

						appList.get(ap).setColour(
								pbData.getServiceCategory().get(i).getColour());

						appList.get(ap)
								.setServiceName(pbData.getServiceCategory()
										.get(i).getService().get(j).getName());

					}
				}

			}

		}

		return appList;
	}

	private PublisherBusiness getBusinessDetails(String bId, String pId)
	{
		PublisherBusiness pBData;

		Query query = new Query();

		query.addCriteria(
				Criteria.where("id").is(bId).and("publisherId").is(pId));

		pBData = this.mongoTemplate.findOne(query, PublisherBusiness.class);

		if (pBData != null)
		{
			return this.pubService.addNaming(pBData);

		}

		return pBData;

	}

	/**
	 * To Create Appointment
	 * 
	 * @param AppointmentBean
	 * @return
	 * @throws ParseException
	 */
	public String createAppointment(AppointmentBean appointBean)
			throws ParseException
	{

		if ((appointBean.getBusinessId() != null)
				&& (appointBean.getPublisherId() != null)
				&& (appointBean.getServiceId() != null))
		{

			List<PublisherBusinessEmployee> finalEmpServiceList, finalPTOList;

			List<Integer> details = getAppointmentDetails(appointBean);
			List<Date> date = toConvertDateForPTO(appointBean, details);

			// PublisherBusiness pbData = getBusinessDetails(appointBean);
			PublisherBusiness pbData = getBusinessDetails(
					appointBean.getBusinessId(), appointBean.getPublisherId());

			if (pbData != null)
			{
				String businessHoursReport = checkBusinessHours(appointBean,
						details, pbData);

				BusinessBreakConfig breakConfig = getBusinessBreakDetails(
						appointBean.getBusinessId());

				if (businessHoursReport.equals("WorkingDay"))
				{
					finalEmpServiceList = checkEmployeeServiceDetails(
							appointBean, details);

					if (finalEmpServiceList != null)
					{
						finalPTOList = checkPtoDetails(finalEmpServiceList,
								date, appointBean);
						if ((finalPTOList != null) && (finalPTOList.size() > 0))
						{
							Collections.sort(finalPTOList,
									PublisherBusinessEmployee.PublisherBusinessEmployeePriority);

							List<AppointmentCalendar> empBreaks = new ArrayList<>();

							if (breakConfig.getBreakStatus() == 1)
							{
								empBreaks = empBreakConfiguration(pbData,
										finalPTOList, details, appointBean,
										breakConfig);
							}

							List<AppointmentCalendar> noServiceList = toFillEmpGapBusinessHourToEmpStartHourWithEmptyObjects(
									finalPTOList, pbData, appointBean);

							if (appointBean.getEmpId() == null)
							{

								String report = null;
								for (PublisherBusinessEmployee emp : finalPTOList)
								{

									report = toCheckEmpAvailabilityAtThatTime(
											emp, appointBean, date, details,
											empBreaks, noServiceList);

									if (report.equals("SUCCESS"))
									{

										report = saveAppointment(emp.getId(),
												appointBean, details);

										break;

									}

								}
								return report;
							} // empId Check

							else
							{
								String report = null;
								List<PublisherBusinessEmployee> empList = new ArrayList<>();

								Query query = new Query();
								query.addCriteria(Criteria.where("id")
										.is(appointBean.getEmpId()));

								PublisherBusinessEmployee emp = this.mongoTemplate
										.findOne(query,
												PublisherBusinessEmployee.class);
								empList.add(emp);

								if (emp != null)
								{

									report = toCheckEmpAvailabilityAtThatTime(
											emp, appointBean, date, details,
											empBreaks, noServiceList);

									if (report.equals("SUCCESS"))
									{
										report = saveAppointment(
												appointBean.getEmpId(),
												appointBean, details);
									}
								}

								return report;

							}

						} // finalPTOList ckeck

						else
						{
							return "LIST_EMPTY";
						}

					}

					else
					{
						return "LIST_EMPTY";
					}

				}

				else if (businessHoursReport.equals("Holiday"))
				{
					return "HOLI_DAY";
				}

				else if (businessHoursReport.equals("NotWorkingHours"))
				{
					return "NotWorkingHours";
				}

				else if (businessHoursReport.equals("TimesMismatched"))
				{
					return "TimesMismatched";
				}

				else
				{
					return "INVALID_DETAILS";
				}

			} // Check Business Data Valid

			else
			{
				return "INVALID_DETAILS";
			}
		}

		else
		{
			return "INVALID_DETAILS";
		}

	}

	/**
	 * To Check The Employee PTO for Particular Service
	 * 
	 * @param details
	 * 
	 * @param AppointmentBean
	 *            object
	 * @return
	 * @throws ParseException
	 */

	private List<PublisherBusinessEmployee> checkEmployeeServiceDetails(
			AppointmentBean appointBean, List<Integer> details)
			throws ParseException
	{
		List<PublisherBusinessEmployee> empList = new ArrayList<>();
		String tmp = details.get(3).toString();
		ObjectId serviceId = new ObjectId(appointBean.getServiceId());
		Query query = new Query();
		query.addCriteria(Criteria.where("businessId")
				.is(appointBean.getBusinessId()).and("publisherId")
				.is(appointBean.getPublisherId()).andOperator(Criteria
						.where("serviceCategory.service.id").is(serviceId)));

		List<PublisherBusinessEmployee> empServiceList = this.mongoTemplate
				.find(query, PublisherBusinessEmployee.class);

		if (empServiceList != null)
		{
			for (PublisherBusinessEmployee emp : empServiceList)
			{
				if (emp.getEmployeeHours().get(tmp).getHoliday() == 0)
				{
					empList.add(emp);
				}
			}
		}

		return empList;
	}

	private List<Date> toConvertDateForPTO(AppointmentBean appointBean,
			List<Integer> details) throws ParseException
	{
		// List<Integer> details = getAppointmentDetails(appointBean);
		List<Date> date = new ArrayList<>();
		int syear = this.details.get(9);
		int smonthIn = this.details.get(10) + 1;
		int sday = this.details.get(11);
		int eday = sday + 1;

		String start = syear + "-" + smonthIn + "-" + sday
				+ "T00:00:00.000+0000";

		String end = syear + "-" + smonthIn + "-" + eday + "T00:00:00.000+0000";

		SimpleDateFormat sdf = new SimpleDateFormat(
				"yyyy-MM-dd'T'HH:mm:ss.SSSZ");

		sdf.setTimeZone(TimeZone.getTimeZone("UTC"));
		date.add(0, sdf.parse(start));
		date.add(1, sdf.parse(end));

		return date;
	}

	private String toCheckEmpAvailabilityAtThatTime(
			PublisherBusinessEmployee emp, AppointmentBean appointBean,
			List<Date> date, List<Integer> details,
			List<AppointmentCalendar> empBreaks,
			List<AppointmentCalendar> noServiceList)
	{

		List<Integer> empMinutes = new ArrayList<>();
		List<Integer> empAptMinutes = new ArrayList<>();
		List<Integer> empBrkMinutes = new ArrayList<>();
		List<Integer> tmpBrkMinutes = new ArrayList<>();

		List<Integer> empAvailMinutes = new ArrayList<>();
		List<Integer> newAptMinutes = new ArrayList<>();

		if ((emp.getEmployeeHours().get(details.get(3).toString())
				.getOpen() <= details.get(4))
				&& (emp.getEmployeeHours().get(details.get(3).toString())
						.getClose() >= details.get(4)))
		{

			// Emp start to end Minutes
			for (int i = emp.getEmployeeHours().get(details.get(3).toString())
					.getOpen(); i <= emp.getEmployeeHours()
							.get(details.get(3).toString()).getClose(); i++)
			{
				empMinutes.add(i);
			}

			// Emp No Service List
			for (int br = 0; br < noServiceList.size(); br++)
			{
				if (noServiceList.get(br).getEmployeeId().equals(emp.getId()))
				{

					for (int p = noServiceList.get(br)
							.getStart(); p <= noServiceList.get(br)
									.getEnd(); p++)
					{
						empAptMinutes.add(p);
					}
				}
			}

			// Emp Breaks

			if (empBreaks.size() > 0)
			{
				for (int br = 0; br < empBreaks.size(); br++)
				{
					if (empBreaks.get(br).getEmployeeId().equals(emp.getId()))
					{

						for (int p = empBreaks.get(br)
								.getStart(); p <= empBreaks.get(br)
										.getEnd(); p++)
						{
							empAptMinutes.add(p);
							empBrkMinutes.add(p);
						}
					}
				}
			}

			// logger.info("Break ----------" + empBrkMinutes);

			Query query = new Query();

			query.addCriteria(Criteria.where("employeeId").is(emp.getId())
					.and("status").in(0, 1)
					.andOperator(Criteria.where("startScheduledTime")
							.gte(date.get(0)).and("endScheduledTime")
							.lt(date.get(1))));
			query.with(
					new Sort(new Order(Direction.ASC, "startScheduledTime")));

			List<AppointmentCalendar> appList = this.mongoTemplate.find(query,
					AppointmentCalendar.class);

			// Emp Booked Appointments
			for (int q = 0; q < appList.size(); q++)
			{

				for (int p = appList.get(q).getStart(); p <= appList.get(q)
						.getEnd(); p++)
				{
					empAptMinutes.add(p);
				}

			}

			// logger.info("empAptMinutes " + empAptMinutes);

			empMinutes.removeAll(empAptMinutes);

			empAvailMinutes = empMinutes;

			// logger.info("empAvailMinutes " + empAvailMinutes);

			int start = details.get(0) * 60 + details.get(1);
			int end = details.get(5) * 60 + details.get(6);

			int count = end - start;

			// New Appointment Start, End Minutes

			for (int r = start; r <= end; r++)
			{

				newAptMinutes.add(r);
				tmpBrkMinutes.add(r);
			}

			// logger.info("newAptMinutes " + newAptMinutes);
			// logger.info("tmpBrkMinutes " + tmpBrkMinutes);

			tmpBrkMinutes.removeAll(empBrkMinutes);

			// logger.info("tmpBrkMinutes " + tmpBrkMinutes);

			newAptMinutes.removeAll(empAvailMinutes);

			// logger.info("newAptMinutes " + newAptMinutes);

			if (newAptMinutes.size() == 0)
			{

				// logger.info("your appointmet will be created..........");
				return "SUCCESS";
			}

			else if (tmpBrkMinutes.size() < newAptMinutes.size())
			{

				// logger.info("your appointmet will not be created..........");
				return "NO_FIT";
			}

			else if (tmpBrkMinutes.size() == 0)
			{

				// logger.info("your appointmet will not be created..........");
				return "BREAK";
			}

			else if (newAptMinutes.size() < count)
			{

				// logger.info("your appointmet will not be created..........");
				return "NO_FIT";
			}

			else
			{

				return "ALL_BOOKED";
			}

		} else
		{

			return "NO_SERVICE";
		}

	}

	private List<PublisherBusinessEmployee> checkPtoDetails(
			List<PublisherBusinessEmployee> empServiceList, List<Date> date,
			AppointmentBean appointBean) throws ParseException
	{

		List<PublisherBusinessEmployee> empPtoTemp = new ArrayList<>();

		for (int i = 0; i < empServiceList.size(); i++)
		{

			Query query = new Query();
			query.addCriteria(Criteria.where("empId")
					.is(empServiceList.get(i).getId())
					.orOperator(Criteria.where("pto.startDate").in(date.get(0)),
							Criteria.where("pto.endDate").in(date.get(0))));

			EmployeePto ptoCheck = this.mongoTemplate.findOne(query,
					EmployeePto.class);

			if ((ptoCheck == null))
			{
				// logger.info("AVAIL EMP---->" +
				// empServiceList.get(i).getId());

				Query query1 = new Query();

				query1.addCriteria(Criteria.where("empId")
						.is(empServiceList.get(i).getId()));

				List<EmployeePto> ptoList = this.mongoTemplate.find(query1,
						EmployeePto.class);

				// logger.info("SIZE--->" + ptoList.size());

				if (ptoList.size() > 0)
				{
					int ps = 0;
					for (Pto p : ptoList.get(0).getPto())
					{
						if (p.getStartDate()
								.compareTo(appointBean.getStartScheduledTime())
								* appointBean.getStartScheduledTime()
										.compareTo(p.getEndDate()) > 0)
						{
							ps = 1;
						}
					}

					if (ps != 1)
					{
						empPtoTemp.add(empServiceList.get(i));
					}
				} else
				{
					empPtoTemp.add(empServiceList.get(i));
				}
			}

		}

		return empPtoTemp;

	}

	public Object checkCalenderEntry(PublisherBusinessEmployee emp,
			AppointmentBean appointBean)
	{
		return appointBean;

	}

	public String saveAppointment(String empId, AppointmentBean appointBean,
			List<Integer> details)
	{

		int start = details.get(4);
		int end = details.get(8);

		int status = 0;
		Date dt = new java.util.Date();
		AppointmentCalendar appointment = new AppointmentCalendar();
		appointment.setBusinessId(appointBean.getBusinessId());
		appointment.setPublisherId(appointBean.getPublisherId());
		appointment.setServiceId(appointBean.getServiceId());
		appointment.setName(appointBean.getName());
		appointment.setEmail(appointBean.getEmail());
		appointment.setPhone(appointBean.getPhone());
		appointment.setStartScheduledTime(appointBean.getStartScheduledTime());
		appointment.setEndScheduledTime(appointBean.getEndScheduledTime());
		appointment.setEmployeeId(empId);
		appointment.setNumberOfPeople(1);
		appointment.setCustomerId(appointBean.getDeviceId());
		appointment.setStatus(status);
		appointment.setStart(start);
		appointment.setEnd(end);
		appointment.setCouponId(appointBean.getCouponId());
		appointment.setCreateDate(dt);
		appointment.setUpdateDate(dt);

		this.pubAppCal.save(appointment);
		return "SUCCESS";

	}

	/**
	 * @param PublisherBusinessEmployee
	 *            Object
	 * @param AppointmentBean
	 *            Object
	 * 
	 * 
	 * @return
	 */
	public List<AppointmentCalendar> checkInCalendorEntry(
			PublisherBusinessEmployee employee, AppointmentBean appointBean)
	{

		// logger.info(" sTART tIME " + appointBean.getStartScheduledTime());

		// logger.info(" eND tIME " + appointBean.getEndScheduledTime());

		// Query query = new Query();
		// query.addCriteria(Criteria.where("employeeId").is(employee.getId())
		// .and("status").in(0, 1)
		// .andOperator(Criteria.where("startScheduledTime").is(appointBean.getStartScheduledTime())
		// .and("endScheduledTime").is(appointBean.getEndScheduledTime())));

		// List<AppointmentCalendar> calendarList = mongoTemplate.findOne(query,
		// AppointmentCalendar.class);

		MatchOperation matchStage = Aggregation
				.match(Criteria.where("employeeId").is(employee.getId())
						.andOperator(Criteria.where("startScheduledTime")
								.is(appointBean.getStartScheduledTime())
								.and("endScheduledTime")
								.is(appointBean.getEndScheduledTime())
								.and("status").in(0, 1)));

		ProjectionOperation projectStage2 = Aggregation.project("id");

		Aggregation aggregation = Aggregation.newAggregation(matchStage,
				projectStage2);

		AggregationResults<AppointmentCalendar> result = this.mongoTemplate
				.aggregate(aggregation, "appointmentCalender",
						AppointmentCalendar.class);

		List<AppointmentCalendar> calendarList = result.getMappedResults();

		return calendarList;

	}

	/**
	 * 
	 * To Check The Business Hours
	 * 
	 * @param details
	 * @param businessDetails
	 * 
	 * @param AppointmentBean
	 * @return
	 * @throws ParseException
	 */
	private String checkBusinessHours(AppointmentBean appointBean,
			List<Integer> details, PublisherBusiness businessDetails)
			throws ParseException
	{
		// To Compare the Dates
		// flag=1 OK
		// flag=0 StartDates lesser than EndDate

		String flag = DateCompare(appointBean, details);

		if (flag.equals("1"))
		{

			if (businessDetails != null)
			{

				// Check the given day is working day or Holiday ?
				if (businessDetails.getBusinessHours()
						.get(details.get(3).toString()).getHoliday() == 0)
				{

					// Check End Time greater than Start Time
					if ((details.get(8) > details.get(4)))
					{

						// Check Working Hours
						if ((businessDetails.getBusinessHours()
								.get(details.get(3).toString())
								.getOpen() <= details.get(4))
								&& (businessDetails.getBusinessHours()
										.get(details.get(3).toString())
										.getClose() >= details.get(8)))
						{
							return "WorkingDay";
						}

						else
						{

							details.add(businessDetails.getBusinessHours()
									.get(details.get(3).toString()).getOpen());
							details.add(businessDetails.getBusinessHours()
									.get(details.get(3).toString()).getClose());
							return "NotWorkingHours";
						}

					} // Check End Time greater than Start Time close

					else
					{
						return "TimesMismatched";
					}

				} // Check the given day is working day or Holiday close

				else
				{
					// day is Holiday
					return "Holiday";
				}

			} // if business Data != null close

			else
			{
				return "Empty";
			} // else business Data = null close

		} // if flag close

		else
		{
			return "TimesMismatched";
		} // else flag close

	}

	public List<Integer> getAppointmentDetails(AppointmentBean appointBean)
	{
		Date openScheduledTime = appointBean.getStartScheduledTime();

		Date endScheduledTime = appointBean.getEndScheduledTime();

		Calendar calendar = Calendar.getInstance();
		calendar.setTime(openScheduledTime);

		Calendar calendar2 = Calendar.getInstance();
		calendar2.setTime(endScheduledTime);

		this.details.add(0, calendar.get(Calendar.HOUR_OF_DAY));
		this.details.add(1, calendar.get(Calendar.MINUTE));
		this.details.add(2, calendar.get(Calendar.SECOND));
		this.details.add(3, calendar.get(Calendar.DAY_OF_WEEK));

		this.details.add(4, this.details.get(0) * 60 + this.details.get(1));

		this.details.add(5, calendar2.get(Calendar.HOUR_OF_DAY));
		this.details.add(6, calendar2.get(Calendar.MINUTE));
		this.details.add(7, calendar2.get(Calendar.SECOND));

		this.details.add(8, this.details.get(5) * 60 + this.details.get(6));

		this.details.add(9, calendar.get(Calendar.YEAR));
		this.details.add(10, calendar.get(Calendar.MONTH));
		this.details.add(11, calendar.get(Calendar.DAY_OF_MONTH));

		this.details.add(12, calendar2.get(Calendar.YEAR));
		this.details.add(13, calendar2.get(Calendar.MONTH));
		this.details.add(14, calendar2.get(Calendar.DAY_OF_MONTH));

		return this.details;

	}

	private String DateCompare(AppointmentBean appointBean,
			List<Integer> details) throws ParseException
	{
		// List<Integer> details = getAppointmentDetails(appointBean);
		int syear = details.get(9);
		int smonthIn = details.get(10) + 1;
		int sday = details.get(11);

		int eyear = details.get(12);
		int emonthIn = details.get(13) + 1;
		int eday = details.get(14);

		String end = eyear + "-" + emonthIn + "-" + eday;
		String start = syear + "-" + smonthIn + "-" + sday;

		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");

		String t = compareDatesByCompareTo(formatter, formatter.parse(start),
				formatter.parse(end));

		return t;
	}

	public static String compareDatesByCompareTo(DateFormat df, Date oldDate,
			Date newDate)
	{

		String flag = null;

		// how to check if date1 is equal to date2
		if (oldDate.compareTo(newDate) == 0)
		{
			flag = "1";
		}

		// checking if date1 is less than date 2
		if (oldDate.compareTo(newDate) < 0)
		{
			flag = "1";
		}

		// how to check if date1 is greater than date2 in java
		if (oldDate.compareTo(newDate) > 0)
		{
			flag = "0";
		}
		return flag;
	}

	public String removePto(EmployeePto removePto)
	{
		String report = "UnSuccess";

		if ((removePto.getEmpId() != null))
		{

			ObjectId id = new ObjectId(removePto.getId());

			Query query = new Query();
			query.addCriteria(Criteria.where("empId").is(removePto.getEmpId())
					.and("pto.id").is(id));
			EmployeePto pubEmpData = this.mongoTemplate.findOne(query,
					EmployeePto.class);

			if (pubEmpData != null)
			{
				System.out.println("REmove PTO" + pubEmpData.getEmpId());
				report = removeService(removePto);
			}
		}

		return report;
	}

	private String removeService(EmployeePto removePto)
	{
		String s;
		ObjectId id = new ObjectId(removePto.getId());

		Query query = Query
				.query(Criteria.where("empId").is(removePto.getEmpId()));

		Update update = new Update().pull("pto", new BasicDBObject("id", id));

		WriteResult pB = this.mongoTemplate.updateFirst(query, update,
				EmployeePto.class);

		if (pB.getN() == 1)
		{
			s = "Success";
		} else
		{
			s = "UnSuccess";
		}

		return s;
	}

	public List<EmployeePto> PtoList(String empId)
	{
		Query listQry = new Query();

		listQry.addCriteria(Criteria.where("empId").is(empId));
		listQry.fields().include("empId");
		listQry.fields().include("pto");
		return this.mongoTemplate.find(listQry, EmployeePto.class);
	}

	public List<Services> publisherBusinessServiceListByEmpId(String empId)
	{

		List<Services> serList = new ArrayList<>();
		Services serv;

		PublisherBusinessEmployee emp;
		if ((empId != null))
		{
			Query query = new Query();
			query.addCriteria(Criteria.where("id").is(empId));
			emp = this.mongoTemplate.findOne(query,
					PublisherBusinessEmployee.class);

			if (emp != null)
			{
				emp = addServiceNaming(emp);

				for (int i = 0; i < emp.getServiceCategory().size(); i++)
				{
					for (int j = 0; j < emp.getServiceCategory().get(i)
							.getService().size(); j++)
					{
						serv = new Services();

						serv.setServiceId(emp.getServiceCategory().get(i)
								.getService().get(j).getId());
						serv.setServiceName(emp.getServiceCategory().get(i)
								.getService().get(j).getName());
						serv.setDescription(emp.getServiceCategory().get(i)
								.getService().get(j).getDescription());

						serv.setDuration(emp.getServiceCategory().get(i)
								.getService().get(j).getDuration());
						serList.add(serv);
					}

				}

			}

			return serList;
		}

		else
		{
			return null;
		}

	}

	public String updateStatus(AppointmentCalendar updateStatus)
	{

		String report = "UnSuccess";

		if ((updateStatus.getId() != null) && (updateStatus.getStatus() != -1))
		{

			Query query = new Query(
					Criteria.where("id").is(updateStatus.getId()));
			Update update = new Update();

			if (updateStatus.getStatus() != -1)
			{
				update.set("status", updateStatus.getStatus());
			}

			if (updateStatus.getUpdateDate() == null)
			{
				Date dt = new java.util.Date();

				update.set("updateDate", dt);
			}

			this.mongoTemplate.upsert(query, update, AppointmentCalendar.class);

			return "Success";
		}

		else
		{
			report = "INVALID";
		}

		return report;

	}

	public List<AppointmentCalendar> consumerAppoinmentList(String deviceId)
	{

		ConsumerBusinessDetails tmp = this.userService
				.getConsumerBusinessDetails(deviceId);

		if (tmp != null)
		{

			Query query = new Query();
			query.fields().exclude("createDate");
			query.fields().exclude("updateDate");

			query.addCriteria(Criteria.where("customerId").is(deviceId)
					.andOperator(Criteria.where("status").ne(3)));

			List<AppointmentCalendar> appointmentData = this.mongoTemplate
					.find(query, AppointmentCalendar.class);

			for (int i = 0; i < appointmentData.size(); i++)
			{
				PublisherBusiness pbData = getBusinessDetails(
						appointmentData.get(i).getBusinessId(),
						appointmentData.get(i).getPublisherId());

				appointmentData.get(i).setServiceName(getServiceName(pbData,
						appointmentData.get(i).getServiceId()));
			}

			return appointmentData;
		}

		else
		{
			return null;
		}

	}

	private String getServiceName(PublisherBusiness pbData, String serviceId)
	{

		String serviceName = null;
		for (int i = 0; i < pbData.getServiceCategory().size(); i++)
		{

			for (int j = 0; j < pbData.getServiceCategory().get(i).getService()
					.size(); j++)
			{

				if (pbData.getServiceCategory().get(i).getService().get(j)
						.getId().equals(serviceId))
				{
					serviceName = pbData.getServiceCategory().get(i)
							.getService().get(j).getName();
				}
			}
		}

		return serviceName;
	}

	// private GetAppointmentConsumerBean getServiceBasedAppointmentDetails(
	// List<AppointmentCalendar> appSplitList, PublisherBusiness pbData,
	// AppointmentBean appCla, List<Integer> details2)
	// {
	// HashMap<Integer, ConsumerHours> map = new HashMap<>();
	// ConsumerHours hours;
	//
	// List<ConsumerServices> serviceList;
	//
	// GetAppointmentConsumerBean setBean = new GetAppointmentConsumerBean();
	//
	// int open = pbData.getBusinessHours().get(this.details.get(3).toString())
	// .getOpen();
	//
	// List<String> empData = getEmpDetailsToPerformParticularService(appCla);
	//
	// int close = pbData.getBusinessHours()
	// .get(this.details.get(3).toString()).getClose();
	//
	// int sHour = open / 60;
	//
	// int eHour = close / 60;
	//
	// int hoursDiff = eHour - sHour;
	// int hrr = sHour;
	// int hStart = sHour;
	//
	// setBean.setBusinessId(pbData.getId());
	// setBean.setBusinessStartTime(sHour);
	// setBean.setBusinessEndTime(eHour);
	// setBean.setDate(appCla.getStartScheduledTime());
	//
	// for (int hour = 0; hour <= hoursDiff; hour++)
	// {
	//
	// hours = new ConsumerHours();
	// hours.setTime(Integer.toString(hrr));
	//
	// serviceList = null;
	// serviceList = new ArrayList<>();
	//
	// for (int serv = 0; serv < 1; serv++)
	// {
	// ConsumerServices service = new ConsumerServices();
	// service.setServiceId(appCla.getServiceId());
	// // service.setServiceName(serviceName);
	// serviceList.add(service);
	//
	// }
	//
	// hours.setServices(serviceList);
	//
	// map.put(hStart, hours);
	// hrr++;
	// hStart++;
	// }
	//
	// setBean.setHours(map);
	//
	// // GetAppointmentConsumerBean finalObject =
	// // toSetServiceBasedAppoinments(
	// // setBean, appSplitList, empData);
	//
	// // toFillServiceNullWithEmptyObjects(finalObject, empData);
	//
	// return setBean;
	// }

}
