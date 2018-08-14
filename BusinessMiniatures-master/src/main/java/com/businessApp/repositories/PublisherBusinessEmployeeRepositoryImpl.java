package com.businessApp.repositories;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;

import com.businessApp.model.EmployeePto;
import com.businessApp.model.PublisherBusinessEmployee;
import com.businessApp.service.PublisherService;

public class PublisherBusinessEmployeeRepositoryImpl
		implements
			CustomPublisherBusinessEmployeeRepository
{

	@Autowired
	MongoTemplate mongoTemplate;

	@Autowired
	PublisherService pubService;

	// @Override
	// public List<PublisherBusinessEmployee> listEmployee(
	// PublisherBusinessEmployee pBE) throws Exception
	// {
	//
	// Query query = new Query(Criteria.where("publisherId")
	// .is(pBE.getPublisherId()).andOperator(
	// Criteria.where("businessId").is(pBE.getBusinessId())));
	//
	// List<PublisherBusinessEmployee> pBEmpList = this.mongoTemplate
	// .find(query, PublisherBusinessEmployee.class);
	//
	// List<PublisherBusinessEmployee> employeeList = new ArrayList<>();
	//
	// PublisherBusinessEmployee tmp;
	//
	// if ((pBEmpList.size()) > 0 && (pBEmpList != null))
	// {
	//
	// for (PublisherBusinessEmployee pBEmp : pBEmpList)
	// {
	// tmp = addNaming(pBEmp);
	// employeeList.add(tmp);
	// }
	// }
	//
	// return employeeList;
	//
	// }

	// private PublisherBusinessEmployee addNaming(PublisherBusinessEmployee
	// pBEmp)
	// {
	//
	// Query qr = new Query();
	// qr.addCriteria(Criteria.where("publisherId").is(pBEmp.getPublisherId())
	// .andOperator(Criteria.where("id").is(pBEmp.getBusinessId())));
	// PublisherBusiness pbBus = this.mongoTemplate.findOne(qr,
	// PublisherBusiness.class);
	//
	// PublisherBusiness tmp;
	//
	// if (pbBus != null)
	// {
	//
	// tmp = this.pubService.addNaming(pbBus);
	//
	// if (tmp.getServiceCategory().size() > 0)
	// {
	//
	// for (int i = 0; i < pBEmp.getServiceCategory().size(); i++)
	// {
	// if (pBEmp.getServiceCategory().get(i).getId()
	// .equals(tmp.getServiceCategory().get(i).getId()))
	// {
	//
	// // Assign Service Category Name
	//
	// pBEmp.getServiceCategory().get(i).setName(
	// tmp.getServiceCategory().get(i).getName());
	//
	// // Assign Service Category Description
	//
	// pBEmp.getServiceCategory().get(i).setDescription(tmp
	// .getServiceCategory().get(i).getDescription());
	//
	// for (int j = 0; j < pBEmp.getServiceCategory().get(i)
	// .getService().size(); j++)
	// {
	//
	// pBEmp.getServiceCategory().get(i).getService()
	// .get(j)
	// .setName(tmp.getServiceCategory().get(i)
	// .getService().get(j).getName());
	//
	// pBEmp.getServiceCategory().get(i).getService()
	// .get(j)
	// .setDescription(tmp.getServiceCategory()
	// .get(i).getService().get(j)
	// .getDescription());
	//
	// }
	// }
	//
	// }
	//
	// }
	//
	// }
	//
	// return pBEmp;
	// }

	@Override
	public String updateEmployee(PublisherBusinessEmployee pubBEmp)
			throws Exception
	{
		if (pubBEmp.getId() != null)
		{
			Query updQry = new Query(Criteria.where("id").is(pubBEmp.getId()));
			Update upd = new Update();

			if (pubBEmp.getFirstName() != null)
			{
				upd.set("firstName", pubBEmp.getFirstName());
			}

			if (pubBEmp.getLastName() != null)
			{
				upd.set("lastName", pubBEmp.getLastName());
			}

			if (pubBEmp.getPhone() != null)
			{
				upd.set("phone", pubBEmp.getPhone());
			}

			if (pubBEmp.getEmployeeHours() != null)
			{
				upd.set("employeeHours", pubBEmp.getEmployeeHours());
			}

			if (pubBEmp.getPriority() != 0)
			{
				upd.set("priority", pubBEmp.getPriority());
			}

			if (pubBEmp.getServiceCategory() != null)
			{
				upd.set("serviceCategory", pubBEmp.getServiceCategory());
			}

			// if (pubBEmp.getPto() != null)
			// {
			// upd.set("pto", pubBEmp.getPto());
			// }

			if (pubBEmp.getUpdatedTime() != null)
			{
				upd.set("updatedTime", pubBEmp.getUpdatedTime());
			}

			this.mongoTemplate.upsert(updQry, upd,
					PublisherBusinessEmployee.class);
			return "Employee Updated Sucessfully";
		} else
		{
			return "INVALID";
		}

	}

	@Override
	public String updateEmployeePto(EmployeePto updatePto) throws Exception
	{

		String report = "UnSuccess";

		if ((updatePto.getEmpId() != null) && (updatePto.getId() != null))
		{

			Query updQry = new Query(
					Criteria.where("id").is(updatePto.getId()));
			Update upd = new Update();

			if (updatePto.getPto() != null)
			{
				upd.set("pto", updatePto.getPto());
			}

			this.mongoTemplate.upsert(updQry, upd, EmployeePto.class);

			return "Success";
		}

		else
		{
			report = "INVALID";
		}

		return report;

	}

}
