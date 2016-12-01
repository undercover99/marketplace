package main.java.service.endpoint;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import main.java.model.constant.Constant;
import main.java.service.representation.Link;
import main.java.service.representation.PartnerRepresentation;
import main.java.service.service.PartnerService;
import main.java.service.serviceImpl.PartnerServiceImpl;
import main.java.util.ElementUtil;


/**
 * This endpoint layer class corresponds to the service layer presented in class. 
 * @author lbo
 *
 */

@Path("/partners")
public class PartnerEndpoint implements PartnerEndpointInterface {

	private static PartnerService partnerService = new PartnerServiceImpl();
	
	@POST//2.1 Need to register and create profile of partners, add partner
	@Consumes({"application/xml"})
	@Produces({"application/xml"})
	public PartnerRepresentation registerPartner(PartnerRepresentation partnerModel){
		String message;
		try {
			partnerService.register(ElementUtil.buildPartnerBean(partnerModel));
			message = "partner successfully registered";
		} catch (NullPointerException e) {
			e.printStackTrace();
			message = "Error. Partner could not be registered";
		}	
		PartnerRepresentation partnerRep = ElementUtil.buildPartnerModel(ElementUtil.buildPartnerBean(partnerModel));
		//set register partner link  
		Link push = new Link("push", Constant.BASE_PATH + "/orders/pushedOrders/{partnerId}=" + partnerRep.getId(), "/", Constant.MEDIA_TYPE_XML );
		//delete partner link
		Link delete = new Link("delete", Constant.BASE_PATH +"/partners/{partnerId}="+ partnerRep.getId(), "/", Constant.MEDIA_TYPE_XML);
		//add product for partner link
		Link addProduct = new Link("add", Constant.BASE_PATH + "/products" , "/", Constant.MEDIA_TYPE_XML);
		
		partnerRep.setLinks(push, delete, addProduct);
		return partnerRep;
		//return Response.ok(message, MediaType.TEXT_XML_TYPE).build();
	}
	
	//the partner can register and after that delete the partner
	@DELETE
	@Consumes({"application/xml"})
	@Path("{partnerId}")
	public Response deletePartner(PartnerRepresentation partnerModel){
		String message;
		
		try{
			partnerService.delete(ElementUtil.buildPartnerBean(partnerModel).getId());
			message = "partner successfully deleted";
		}catch(NullPointerException e){
			e.printStackTrace();
			message = "Error. Partner could not be deleted.";
		}
		return Response.ok(message, MediaType.TEXT_XML_TYPE).build();
	}
	
	
	@GET//get partner info, testing purpose 
	@Consumes({"application/xml"})
	@Path("/{partnerId}")
	public PartnerRepresentation getPartner(@PathParam("partnerId") int orderId){
		return ElementUtil.buildPartnerModel(partnerService.get(orderId));
	}

	@GET//get partner info given login
	@Produces({"application/xml"})
	@Path("/{login}")
	@Override
	public PartnerRepresentation get(@PathParam("login") String login) {
		return ElementUtil.buildPartnerModel(partnerService.get(login));
	}

}
