/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controller;

import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import Models.*;
import Service.*;
import jakarta.ws.rs.core.Response;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author jarro
 */
@Path("/jokeserver")
public class ServerController {
    private Service_Interface service;

    public ServerController() {
        this.service = new Service_Impl();
    }

    @POST
    @Path("/addUser")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response register(User user) {
        Integer userId = (int) (Math.random() * 10000);
        while (service.searchForUser(userId)) {
            userId = (int) (Math.random() * 10000);
        }
        user.setUserID(userId);
        return Response.ok().entity(service.addUser(user)).build();
    }

    @POST
    @Path("/login")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response login(User user) {
        return Response.ok().entity(service.login(user)).build();
    }

    @POST
    @Path("/getRandomJoke")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response getJoke(List<Integer> categoryIds) {
        return Response.ok().entity(service.getRandomJoke(categoryIds)).build();
    }

    @POST
    @Path("/addJoke")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response addJoke(Joke joke) {
        return Response.ok().entity(service.addJoke(joke)).build();
    }

    @GET
    @Path("/deleteJoke/{jokeId}")
    public Response deleteJoke(@PathParam("jokeId") Integer jokeId) {
        return Response.ok().entity(service.deleteJoke(jokeId)).build();
    }

    @POST
    @Path("/updateJoke")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response updateJoke(Joke joke) {
        return Response.ok().entity(service.updateJoke(joke)).build();
    }

    @POST
    @Path("/addCategory")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response addCategory(Category category) {
        return Response.ok().entity(service.addCategory(category)).build();
    }

    @GET
    @Path("/getUserJokes/{userId}")
    @Consumes(MediaType.APPLICATION_JSON)
    public List<Joke> getUserJokes(@PathParam("userId") Integer userId) {
        return service.getUserJokes(userId);
    }
    
    @GET
    @Path("/getCategories")
    public List<Category> getCategories() {
        return service.getAllCategories();
    }
    
    @GET
    @Path("/getJoke/{jokeId}")
    public Response getJoke(@PathParam("jokeId") Integer jokeId) {
        return Response.ok().entity(service.getJoke(jokeId)).build();
    }
}
