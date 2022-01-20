package de.unidue.inf.is.stores;

import de.unidue.inf.is.domain.Drive;
import de.unidue.inf.is.domain.User;
import de.unidue.inf.is.utils.DBUtil;

import java.io.Closeable;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public final class DriveStore implements Closeable {
    private Connection connection;
    private boolean complete;

    public DriveStore() throws StoreException{
        try {
            connection = DBUtil.getExternalConnection();
            connection.setAutoCommit(false);
        }
        catch (SQLException e) {
            throw new StoreException(e);
        }
    }

    public ArrayList<Drive> getOpenDrives() throws StoreException {
        ArrayList<Drive> result = new ArrayList<>();

        try {
            PreparedStatement ps = connection.prepareStatement("select f.fid, f.startort, f.zielort, f.fahrtkosten, f.maxPlaetze - SUM(CASE WHEN r.anzPlaetze <> NULL THEN r.anzPlaetze ELSE 0 END) as freiplätze, t.icon from dbp105.fahrt f LEFT JOIN dbp105.reservieren r ON f.fid = r.fahrt INNER JOIN dbp105.transportmittel t ON f.transportmittel = t.tid WHERE f.status = 'offen' GROUP BY f.startort, f.zielort, f.fid, f.maxPlaetze, f.fahrtkosten, t.icon");
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Drive drive = new Drive();
                drive.setStartort(rs.getString("startort"));
                drive.setZielort(rs.getString("zielort"));
                drive.setFreiplätze(rs.getInt("freiplätze"));
                drive.setFahrtkosten(rs.getFloat("fahrtkosten"));
                drive.setIcon(rs.getString("icon"));
                drive.setFID(rs.getInt("fid"));
                result.add(drive);
            }
        } catch (SQLException e) {
            throw new StoreException(e);
        }
        return result;
    }

    public ArrayList<Drive> getReservedDrives() throws StoreException {
        ArrayList<Drive> result = new ArrayList<>();
        User usr = new User();
        usr.setBID(User.loggedInBID);

        try {
            PreparedStatement ps = connection.prepareStatement("select * from dbp105.reservieren r INNER JOIN dbp105.benutzer b ON b.bid = ? INNER JOIN dbp105.fahrt f ON r.fahrt = f.fid INNER JOIN dbp105.transportmittel t ON t.tid = f.transportmittel WHERE r.kunde = b.bid");
            ps.setInt(1, usr.getBID());
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Drive drive = new Drive();
                drive.setStartort(rs.getString("startort"));
                drive.setZielort(rs.getString("zielort"));
                drive.setStatus(rs.getString("status"));
                drive.setIcon(rs.getString("icon"));
                drive.setFID(rs.getInt("fid"));

                result.add(drive);
            }
        } catch (SQLException e) {
            throw new StoreException(e);
        }
        return result;
    }

    public Drive getDriveInformation(Drive drive) throws StoreException {

        try {
            PreparedStatement ps = connection.prepareStatement("select b.name, f.fahrtdatumzeit, f.startort, f.zielort, f.maxPlaetze - SUM(CASE WHEN r.anzPlaetze <> NULL THEN r.anzPlaetze ELSE 0 END) as freiplätze, f.fahrtkosten, t.icon, f.status from dbp105.fahrt f LEFT JOIN dbp105.reservieren r ON f.fid = r.fahrt  INNER JOIN dbp105.transportmittel t ON f.transportmittel = t.tid INNER JOIN dbp105.benutzer b ON b.bid = f.anbieter WHERE fid = ? GROUP BY f.startort, f.zielort, f.fid, f.maxPlaetze, f.fahrtkosten, t.icon, f.status, b.name, f.fahrtdatumzeit");
            PreparedStatement ps2 = connection.prepareStatement("select f.beschreibung from dbp105.fahrt f WHERE f.fid = ?");
            ps.setInt(1, drive.getFID());
            ps2.setInt(1, drive.getFID());
            ResultSet rs = ps.executeQuery();
            ResultSet r2 = ps2.executeQuery();
            while (rs.next()) {
                drive.setStartort(rs.getString("startort"));
                drive.setZielort(rs.getString("zielort"));
                drive.setStatus(rs.getString("status"));
                drive.setIcon(rs.getString("icon"));
                drive.setFahrtkosten(rs.getFloat("fahrtkosten"));
                drive.setFahrtdatumzeit(rs.getTimestamp("fahrtdatumzeit"));
                drive.setFreiplätze(rs.getInt("freiplätze"));
                drive.setAnbieter(rs.getString("name"));
            }
            while (r2.next()){
                drive.setBeschreibung(r2.getString(1));
            }
        } catch (SQLException e) {
            throw new StoreException(e);
        }
        return drive;
    }
    public void storeNewDrive(Drive newDrive) throws StoreException{



        try {
            PreparedStatement ps = connection.prepareStatement("INSERT INTO dbp105.fahrt (startort,zielort,fahrtdatumzeit,maxPlaetze,fahrtkosten,anbieter,transportmittel,beschreibung) VALUES (?,?,?,?,?,?,?,?)");


            ps.setString(1,newDrive.getStartort());
            ps.setString(2,newDrive.getZielort());
            ps.setTimestamp(3,newDrive.getFahrtdatumzeit());
            ps.setInt(4,newDrive.getMaxplätze());
            ps.setFloat(5,newDrive.getFahrtkosten());
            ps.setInt(7,newDrive.getTransportmittel());
            ps.setString(8,newDrive.getBeschreibung());
            ps.setInt(6,User.loggedInBID);
            ps.executeUpdate();

            System.out.println(newDrive.getFahrtkosten());

        } catch (SQLException e) {
            throw new StoreException(e);
        }

    }

    public boolean checkForLicense(Drive newDrive) {
        try {
            PreparedStatement ps = connection.prepareStatement("SELECT * FROM dbp105.fahrerlaubnis WHERE fahrer=?");
            ps.setInt(1,User.loggedInBID);
            ResultSet rs= ps.executeQuery();
            while (rs.next()) {
                if (rs.getString("ablaufdatum").compareTo(String.valueOf(newDrive.getFahrtdatumzeit()))>0) {
                    System.out.println("License ends on: " + rs.getString("ablaufdatum"));
                    System.out.println("Drive date: "+ String.valueOf(newDrive.getFahrtdatumzeit()));
                    return true;
                }

            }
                return false;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return false;
        }
    }




    public void complete() {
        complete = true;
    }

    @Override
    public void close() throws IOException {
        if (connection != null) {
            try {
                if (complete) {
                    connection.commit();
                }
                else {
                    connection.rollback();
                }
            }
            catch (SQLException e) {
                throw new StoreException(e);
            }
            finally {
                try {
                    connection.close();
                }
                catch (SQLException e) {
                    throw new StoreException(e);
                }
            }
        }
    }
}
