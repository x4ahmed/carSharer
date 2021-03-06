package de.unidue.inf.is;

import de.unidue.inf.is.domain.Drive;
import de.unidue.inf.is.stores.DriveStore;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Servlet of the view_main page which shows all the open trips also the reserved trips for
 * our internal logged in user
 *
 * @autor Ahmed Omran
 */
public class ViewMainServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
       // System.out.println("2025-01-17.11.22.33.123456".compareTo("2023-05-17.11.22.33.123456"));
        req.setAttribute("pagetitle", "-Main");
        DriveStore drive = new DriveStore();
        ArrayList<Drive> listOfReservedDrives = drive.getReservedDrives();
        ArrayList<Drive> listOfOpenDrives = drive.getOpenDrives();
        //listOfReservedDrives.stream().forEach(System.out::println);
        drive.complete();
        drive.close();
        req.setAttribute("reservedDrive", listOfReservedDrives);
        req.setAttribute("openDrive", listOfOpenDrives);
        req.getRequestDispatcher("/viewMain.ftl").forward(req, resp);
    }
}