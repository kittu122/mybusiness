package com.businessApp.service;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import org.bson.BsonBinarySubType;
import org.bson.types.Binary;
import org.bson.types.ObjectId;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;

import com.businessApp.bean.PublisherBusinessServices;
import com.businessApp.bean.QRCodeBean;
import com.businessApp.bean.UpdatePBColor;
import com.businessApp.model.Business;
import com.businessApp.model.BusinessBreakConfig;
import com.businessApp.model.PublisherBusiness;
import com.businessApp.model.QRCode;
import com.businessApp.model.ServiceCategory;
import com.businessApp.model.ServiceType;
import com.businessApp.model.User;
import com.businessApp.repositories.BusinessBreakConfigRepository;
import com.businessApp.repositories.BusinessRepository;
import com.businessApp.repositories.PublisherBusinessRepository;
import com.businessApp.repositories.QRCodeRepository;
import com.mongodb.BasicDBObject;
import com.mongodb.WriteResult;

@Service
public class PublisherService
{

	private static Logger pubServLogger = LoggerFactory
			.getLogger(PublisherService.class);

	@Autowired
	private PublisherBusinessRepository pubBusRepo;

	@Autowired
	MongoTemplate mongoTemplate;

	@Autowired
	private QRCodeRepository qrCodeRep;

	@Autowired
	UserService userService;

	@Autowired
	BusinessRepository businessRepo;

	@Autowired
	BusinessService businessService;

	@Autowired
	BusinessBreakConfigRepository bussinessBreak;

	// @Autowired
	// List<ServiceType> tmp;

	public String save(PublisherBusiness publBusiness) throws Exception
	{

		List<ServiceType> tmp = null;
		int k = 0;

		Query query = new Query();
		query.addCriteria(
				Criteria.where("id").is(publBusiness.getPublisherId()));
		User userData = this.userService.mongoTemplate.findOne(query,
				User.class);

		if (userData != null)
		{
			if (userData.getId().equals(publBusiness.getPublisherId()))
			{

				this.pubBusRepo.save(publBusiness);

				tmp = new ArrayList<>();

				for (int i = 0; i < publBusiness.getServiceCategory()
						.size(); i++)
				{
					for (int j = 0; j < publBusiness.getServiceCategory().get(i)
							.getService().size(); j++)
					{

						if (publBusiness.getServiceCategory().get(i)
								.getService().get(j).getType() == 1)
						{

							tmp.add(k, publBusiness.getServiceCategory().get(i)
									.getService().get(j));

							String bId = publBusiness.getBusinessId();
							String SerCatId = publBusiness.getServiceCategory()
									.get(i).getId();

							addServiceToBusiness(bId, SerCatId, tmp);
							tmp.remove(k);
						}
					}

				}

				return publBusiness.getId();

			} else
			{

				return "UNSUCCESS";

			}
		}

		else
		{

			return "UNSUCCESS";

		}

	}

	private void addServiceToBusiness(String bId, String serCatId,
			List<ServiceType> sevr)
	{

		ObjectId ob = new ObjectId(serCatId);

		for (int i = 0; i < sevr.size(); i++)
		{
			if ((sevr.get(i).getName() != null)
					&& (sevr.get(i).getDescription() != null))
			{

				sevr.get(i).setDuration(0);
				sevr.get(i).setPrice(0);
				sevr.get(i).setUnit(null);

				Query query = new Query();
				query.addCriteria(Criteria.where("serviceCategory.id").is(ob));

				Update update = new Update();

				update.addToSet("serviceCategory.$.service", sevr.get(i));

				this.mongoTemplate.findAndModify(query, update, Business.class);

			}
		}

	}

	public List<PublisherBusiness> businessListBypublisherId(String publisherId)
			throws Exception
	{

		Query query = new Query();
		query.addCriteria(Criteria.where("publisherId").is(publisherId));
		List<PublisherBusiness> tmpBusinessList = this.mongoTemplate.find(query,
				PublisherBusiness.class);

		List<PublisherBusiness> BusinessList = new ArrayList<>();
		PublisherBusiness tmp;

		if ((tmpBusinessList.size()) > 0 && (tmpBusinessList != null))
		{

			for (PublisherBusiness pb : tmpBusinessList)
			{
				tmp = addNaming(pb);
				BusinessList.add(tmp);
			}
		}

		return BusinessList;

	}

	public PublisherBusiness addNaming(PublisherBusiness pb)
	{
		Query qr = new Query();
		qr.addCriteria(Criteria.where("id").is(pb.getBusinessId()));
		Business businessDATA = this.mongoTemplate.findOne(qr, Business.class);

		if (businessDATA != null)
		{

			for (int i = 0; i < pb.getServiceCategory().size(); i++)

			{

				for (int p = 0; p < businessDATA.getServiceCategory()
						.size(); p++)

				{
					if (businessDATA.getServiceCategory().get(p).getId()
							.equals(pb.getServiceCategory().get(i).getId()))

					{

						// Assign ServiceCategory Name
						pb.getServiceCategory().get(i).setName(businessDATA
								.getServiceCategory().get(p).getName());

						// Assign ServiceCategory Description
						pb.getServiceCategory().get(i).setDescription(
								businessDATA.getServiceCategory().get(p)
										.getDescription());

						if (pb.getServiceCategory().get(i).getType() == 0)
						{
							pb.getServiceCategory().get(i)
									.setColour(businessDATA.getServiceCategory()
											.get(p).getColour());
						}

						// Check the sizes of services in Business and Publisher
						// Business

						for (int j = 0; j < pb.getServiceCategory().get(i)
								.getService().size(); j++)
						{

							for (int q = 0; q < businessDATA
									.getServiceCategory().get(p).getService()
									.size(); q++)
							{

								if (businessDATA.getServiceCategory().get(p)
										.getService().get(q).getId()
										.equals(pb.getServiceCategory().get(i)
												.getService().get(j).getId()))
								{

									// Assign Service Name
									pb.getServiceCategory().get(i).getService()
											.get(j)
											.setName(businessDATA
													.getServiceCategory().get(p)
													.getService().get(q)
													.getName());

									// Assign Service Description
									pb.getServiceCategory().get(i).getService()
											.get(j)
											.setDescription(businessDATA
													.getServiceCategory().get(p)
													.getService().get(q)
													.getDescription());

								}

							}

						}

					}

				}

			}
		} // if null check

		return pb;

	}

	public String addServiceToPublisherBusiness(
			PublisherBusinessServices addService)
	{

		String report = "UnSuccess";

		if ((addService.getPublisherBusinessId() != null))
		{

			Query query = new Query();
			query.addCriteria(Criteria.where("id")
					.is(addService.getPublisherBusinessId()));
			PublisherBusiness pubBussinessData = this.mongoTemplate
					.findOne(query, PublisherBusiness.class);

			if (pubBussinessData != null)
			{

				if (pubBussinessData.getServiceCategory().size() > 0)
				{
					report = addService(addService);
				}

			}

		}

		return report;

	}

	private String addService(PublisherBusinessServices addService)
	{

		String s;
		ObjectId ob = addService.getServiceCatId();

		Query query = new Query();
		query.addCriteria(Criteria.where("serviceCategory.id").is(ob));
		Update update = new Update();

		update.addToSet("serviceCategory.$.service", addService.getService());

		PublisherBusiness add = this.mongoTemplate.findAndModify(query, update,
				PublisherBusiness.class);

		if (add != null)
		{
			s = "Success";
		} else
		{
			s = "UnSuccess";
		}

		return s;

	}

	public String deleteBusinessById(String businessId)
	{
		Query query = new Query();
		query.addCriteria(Criteria.where("id").is(businessId));
		PublisherBusiness deletedEmp = this.mongoTemplate.findAndRemove(query,
				PublisherBusiness.class);

		if (deletedEmp != null)
		{
			return "SUCCESS";
		}

		else
		{
			return "UNSUCCESS";
		}
	}

	public String removeServiceFromPublisherBusiness(
			PublisherBusinessServices removeService)
	{

		String report = "UnSuccess";

		if ((removeService.getPublisherBusinessId() != null))
		{

			Query query = new Query();
			query.addCriteria(Criteria.where("id")
					.is(removeService.getPublisherBusinessId()));
			PublisherBusiness pubBussinessData = this.mongoTemplate
					.findOne(query, PublisherBusiness.class);

			if (pubBussinessData != null)
			{

				if (pubBussinessData.getServiceCategory().size() > 0)
				{
					report = removeService(removeService);
				}
			}
		}

		return report;
	}

	private String removeService(PublisherBusinessServices removeService)
	{
		String s;
		Query query = Query.query(Criteria.where("serviceCategory.service.id")
				.is(removeService.getServiceId()));

		Update update = new Update().pull("serviceCategory.$.service",
				new BasicDBObject("id", removeService.getServiceId()));

		WriteResult pB = this.mongoTemplate.updateFirst(query, update,
				PublisherBusiness.class);

		if (pB.getN() == 1)
		{
			// success
			s = "Success";
		} else
		{
			s = "UnSuccess";
		}

		return s;

	}

	public String updatePublisherBusiness(PublisherBusiness updatePublBusiness)
			throws Exception
	{

		return this.pubBusRepo.updateBusines(updatePublBusiness);

	}

	public List<PublisherBusiness> publisherBusinessListByBId(String businessId)
			throws Exception
	{

		Query query = new Query();
		query.addCriteria(Criteria.where("businessId").is(businessId));

		List<PublisherBusiness> pubData = this.mongoTemplate.find(query,
				PublisherBusiness.class);

		List<PublisherBusiness> BusinessList = new ArrayList<>();
		PublisherBusiness tmp;

		if ((pubData.size()) > 0 && (pubData != null))
		{

			for (PublisherBusiness pb : pubData)
			{
				tmp = addNaming(pb);
				BusinessList.add(tmp);
			}
		}

		return BusinessList;

	}

	public String updatePubisherBusinessServiceCatColor(
			UpdatePBColor updatePublBusinessColor)
	{
		String report = "UnSuccess";

		if ((updatePublBusinessColor.getId() != null)
				&& (updatePublBusinessColor.getServCatId() != null))
		{

			Query query = new Query();
			query.addCriteria(Criteria.where("id")
					.is(updatePublBusinessColor.getId())
					.andOperator(Criteria.where("serviceCategory.id")
							.is(updatePublBusinessColor.getServCatId())));

			Update update = new Update();

			update.set("serviceCategory.$.colour",
					updatePublBusinessColor.getColour());
			update.set("serviceCategory.$.type",
					updatePublBusinessColor.getType());

			WriteResult tmp = this.mongoTemplate.updateFirst(query, update,
					PublisherBusiness.class);

			if (tmp.getN() == 1)
			{
				return "Success";

			}

			else
			{
				return "UnSuccess";
			}

		}

		else
		{
			report = "INVALID";
		}

		return report;
	}

	public void saveQRCode(QRCodeBean qrb) throws IOException
	{

		if ((qrb.getId() != null))
		{

			QRCode qr = new QRCode();

			qr.setId(qrb.getId());
			qr.setQrCode(new Binary(BsonBinarySubType.BINARY,
					qrb.getQrCode().getBytes("UTF-8")));

			this.qrCodeRep.save(qr);
		}

	}

	public Object getpublisherBusinessQRCode(String businessId)
			throws UnsupportedEncodingException

	{

		if (businessId != null)
		{
			Query query = new Query();
			query.addCriteria(Criteria.where("id").is(businessId));

			QRCode data = this.mongoTemplate.findOne(query, QRCode.class);

			String qrCodeString = new String(data.getQrCode().getData(),
					"UTF-8");

			return qrCodeString;

		}

		else
		{
			return null;
		}

	}

	public String saveBreakConfig(BusinessBreakConfig businesBreak)
	{
		Query query = new Query();
		query.addCriteria(Criteria.where("bId").is(businesBreak.getbId()));

		BusinessBreakConfig tmp = this.mongoTemplate.findOne(query,
				BusinessBreakConfig.class);

		if (tmp == null)
		{
			this.bussinessBreak.save(businesBreak);
			return "Break config details has been successfully stored";
		}

		else
		{
			return "Break config details has not been successfully stored !";
		}

	}

	public String updateBreakConfig(BusinessBreakConfig businesBreak)
	{
		String report = "UnSuccess";

		if ((businesBreak.getbId() != null) && (businesBreak.getId() != null))
		{

			Query query = new Query();

			query.addCriteria(
					Criteria.where("bId").is(businesBreak.getbId()).andOperator(
							Criteria.where("id").is(businesBreak.getId())));

			Update update = new Update();

			if ((businesBreak.getFrequency() != null))
			{
				update.set("frequency", businesBreak.getFrequency());

			}

			if ((businesBreak.getInterval() > 0))
			{

				update.set("interval", businesBreak.getInterval());
			}

			if ((businesBreak.getBreakStatus() == 0)
					|| (businesBreak.getBreakStatus() == 1))
			{

				update.set("breakStatus", businesBreak.getBreakStatus());
			}

			WriteResult tmp = this.mongoTemplate.upsert(query, update,
					BusinessBreakConfig.class);

			if (tmp.getN() == 1)
			{
				return "Success";

			}

			else
			{
				return "UnSuccess";
			}

		}

		else
		{
			report = "INVALID";
		}

		return report;

	}

	public Object getBreakConfigDetails(String businessId)
	{
		Query query = new Query();
		query.addCriteria(Criteria.where("bId").is(businessId));

		return this.mongoTemplate.findOne(query, BusinessBreakConfig.class);
	}

	public Object publisherBusinessByBId(String businessId)
	{
		Query query = new Query();
		query.addCriteria(Criteria.where("id").is(businessId));
		query.fields().include("id");
		query.fields().include("publisherId");
		query.fields().include("businessId");
		query.fields().include("name");
		query.fields().include("serviceCategory");

		PublisherBusiness pbData = this.mongoTemplate.findOne(query,
				PublisherBusiness.class);

		if (pbData != null)
		{
			addNaming(pbData);
		}

		return pbData;

	}

	public String updateServicCategories(PublisherBusiness updatePublBusiness)
			throws Exception
	{

		return this.pubBusRepo.updateBusines(updatePublBusiness);

	}

	public String updateParticularService(
			PublisherBusinessServices updatePubService)
	{

		String s;
		ObjectId ob = updatePubService.getServiceCatId();

		Query query = new Query();
		query.addCriteria(Criteria.where("id")
				.is(updatePubService.getPublisherBusinessId())
				.and("serviceCategory.id").is(ob));

		PublisherBusiness tmp = this.mongoTemplate.findOne(query,
				PublisherBusiness.class);

		if (tmp != null)
		{
			for (ServiceCategory serCat : tmp.getServiceCategory())
			{
				if (serCat.getId().equals(ob.toString()))
				{
					for (ServiceType service : serCat.getService())
					{

						if (service.getId()
								.equals(updatePubService.getService().getId()))
						{

							Query query2 = new Query();
							query2.addCriteria(Criteria.where("id")
									.is(updatePubService
											.getPublisherBusinessId())
									.and("serviceCategory.id").is(ob));

							Update upd = new Update();

							if ((updatePubService.getService().getPrice() != 0))
							{
								upd.set("price", updatePubService.getService()
										.getPrice());
							}

							this.mongoTemplate.upsert(query2, upd,
									PublisherBusiness.class);

						}

					}

				}
			}

			s = "Success";
		}

		else
		{
			s = "UnSuccess";
		}

		return s;

	}

	// public static byte[] LoadImage(String filePath) throws Exception
	// {
	// File file = new File(filePath);
	// int size = (int) file.length();
	// byte[] buffer = new byte[size];
	// FileInputStream in = new FileInputStream(file);
	// in.read(buffer);
	// in.close();
	// return buffer;
	// }

}
