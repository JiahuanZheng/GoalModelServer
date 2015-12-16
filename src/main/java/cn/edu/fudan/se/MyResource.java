package cn.edu.fudan.se;

import javax.ws.rs.*;
import javax.ws.rs.core.*;
import cn.edu.fudan.se.utility.*;
import java.util.List;
/**
 * Root resource (exposed at "myresource" path)
 */
@Path("myresource")
public class MyResource {

    /**
     * Method handling HTTP GET requests. The returned object will be sent
     * to the client as "text/plain" media type.
     *
     * @return String that will be returned as a text/plain response.
     */
    @GET
    @Produces(MediaType.TEXT_PLAIN)
    @Path("/getGoalModelList")
    public String getGoalModelList() {
        List<String> ls = GoalModelOperations.getGoalModelList();
	if(ls.size() == 0) return "{}";
        StringBuffer sb = new StringBuffer("{");
        sb.append(ls.get(0));
        for(int i = 1; i < ls.size(); i++){
  		sb.append("," + ls.get(i));
        }
	sb.append("}");
        return sb.toString();
    }
    @GET
    @Produces(MediaType.TEXT_PLAIN)
    @Path("/getTemplatesList/{goalModelName}")
    public String getTemplatesList(@PathParam("goalModelName") String goalModelName) {

//System.out.println("Goal Model Name : " + goalModelName);
//add escape symbol when there exists space symbol in the goal model name.
     //   goalModelName = escapeBlankspaceOfPath(goalModelName);

        List<String> ls = GoalModelOperations.getTemplatesList("", goalModelName);
	if(ls.size() == 0) return "{}";
        StringBuffer sb = new StringBuffer("{");
        sb.append(ls.get(0));
        for(int i = 1; i < ls.size(); i++){
  		sb.append("," + ls.get(i));
        }
	sb.append("}");
        return sb.toString();
    }

    @GET
    @Produces(MediaType.TEXT_PLAIN)
    @Path("/getTemplateContent/{goalModelName}/{templateName}")
    public String getTemplateContent(@PathParam("goalModelName") String goalModelName,
					@PathParam("templateName") String templateName){
	return GoalModelOperations.getTemplateContent("",goalModelName,templateName);
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Path("/writeTemplateInstance")
    public Response writeTemplateInstance(TemplateInstance input){
       //TODO: the submitterName should be stored in database.here, we just discard it.
       System.out.println("Post Request.");
       GoalModelOperations.writeTemplateInstance("", input.goalModelName, input.templateName, input.templateInstance);
       return Response.status(200).entity("succeed").build();
    }



    private String escapeBlankspaceOfPath(String s){
   	StringBuffer sb = new StringBuffer(s);
	for (int fromIndex = 0; fromIndex < sb.length(); fromIndex += 2) {
		fromIndex = sb.indexOf(" ", fromIndex);
		if (fromIndex < 0)
			break;
		sb.insert(fromIndex, "\\");
	}
	return sb.toString();
    }
 
}


























