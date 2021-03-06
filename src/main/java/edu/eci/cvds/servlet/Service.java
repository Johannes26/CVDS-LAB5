package edu.eci.cvds.servlet;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Writer;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;

import edu.eci.cvds.servlet.model.Todo;

@WebServlet(
        urlPatterns = "/servicesWeb"
)
public class Service extends HttpServlet{
	private ArrayList<Todo> todos = new ArrayList<Todo>();
	
	
  @Override
  protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
	 
      Writer responseWriter = resp.getWriter();
      try {
          Optional <Integer> optId = Optional.ofNullable(Integer.parseInt(req.getParameter("id")));
          Integer id = optId.isPresent() ? optId.get():1;
          todos.add(getTodo(id));
          resp.setStatus(HttpServletResponse.SC_OK);
          responseWriter.write(todosToHTMLTable(todos));
          responseWriter.flush();
      }catch ( NumberFormatException e){
          responseWriter.write("Requerimiento Inv�lido");
          resp.setStatus(HttpServletResponse.SC_NOT_FOUND);
      }catch (FileNotFoundException e){
          responseWriter.write("No encontrado.");
          resp.setStatus(HttpServletResponse.SC_NOT_FOUND);
      }catch (MalformedURLException e){
          responseWriter.write("Error interno en el Servidor ");
      }catch (Exception e){
      responseWriter.write("requerimiento inv�lido");
      }
  }
  
  @Override
  protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
	 
      Writer responseWriter = resp.getWriter();
      try {
          Optional <Integer> optId = Optional.ofNullable(Integer.parseInt(req.getParameter("id")));
          Integer id = optId.isPresent() ? optId.get():1;
          todos.add(getTodo(id));
          resp.setStatus(HttpServletResponse.SC_OK);
          responseWriter.write(todosToHTMLTable(todos));
          responseWriter.flush();
      }catch ( NumberFormatException e){
          responseWriter.write("Requerimiento Inv�lido");
          resp.setStatus(HttpServletResponse.SC_NOT_FOUND);
      }catch (FileNotFoundException e){
          responseWriter.write("No encontrado.");
          resp.setStatus(HttpServletResponse.SC_NOT_FOUND);
      }catch (MalformedURLException e){
          responseWriter.write("Error interno en el Servidor ");
      }catch (Exception e){
      responseWriter.write("requerimiento inv�lido");
      }
  }

   public static Todo getTodo(int id) throws MalformedURLException, IOException {
       URL urldemo = new URL("https://jsonplaceholder.typicode.com/todos/" + id);
       URLConnection yc = urldemo.openConnection();
       BufferedReader in = new BufferedReader(new InputStreamReader(yc.getInputStream()));
       Gson gson = new Gson();
       Todo todo = gson.fromJson(in, Todo.class);
       in.close();
       return todo;
   }

   private static String todoToHTMLRow(Todo todo) {
       return new StringBuilder("<tr>")
           .append("<td>")
           .append(todo.getUserId())
           .append("</td><td>")
           .append(todo.getId())
           .append("</td><td>")
           .append(todo.getTitle())
           .append("</td><td>")
           .append(todo.getCompleted())
           .append("</td>")
           .append("</tr>")
           .toString();
   }

   public static String todosToHTMLTable(List<Todo> todoList) {
       StringBuilder stringBuilder = new StringBuilder("<table>")
           .append("<tr>")
           .append("<th>User Id</th>")
           .append("<th>Id</th>")
           .append("<th>Title</th>")
           .append("<th>Completed</th>")
           .append("</tr>");

       for (Todo todo : todoList) {
           stringBuilder.append(todoToHTMLRow(todo));
       }

       return stringBuilder.append("</table>").toString();
   }
}