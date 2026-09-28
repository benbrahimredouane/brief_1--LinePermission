package ma.youcode.lineperm.service;


import java.time.LocalDateTime;

import java.util.List;

import ma.youcode.lineperm.dao.LogDao;
import ma.youcode.lineperm.model.Log;
import ma.youcode.lineperm.model.Log.Action;
import ma.youcode.lineperm.model.Log.Status;




public class LogService {

    private LogDao logDao;

    public LogService(LogDao logDao) {
        this.logDao = logDao;
    }

    public void addLog(LocalDateTime date, String ownerFile, String fileName,
            Action action, Status status, int UserId) {

        Log log = new Log(null, null, ownerFile, fileName, action, status, UserId);
        logDao.save(log);

    }

    public void countallactions() {
        long c = logDao.compterTotal();
        System.out.println("total actions : " + c);

    }

    public void countrefusedactions() {
        long c = logDao.compterTotalResused();
        System.out.println("number refuse: " + c);

    }

    public void usersdisctint() {
        List<String> users = logDao.usersdisctint();

        if (users != null) {
            for (String user : users) {
                System.out.println("user name: " + user);
            }
        }

    }

    public void actionsPerUser() {

        if (logDao.actionsperusers() != null) {
            for (String login : logDao.actionsperusers().keySet()) {
                System.out.println("userName = " + login + " actions: " + logDao.actionsperusers().get(login));
            }
        }

    }

    public void top3files() {
      
         if(logDao.top3() != null){
            for(String file : logDao.top3().keySet() ){
                System.out.println("filename: "+ file + " times: "+logDao.top3().get(file));

            }
         }
    }

    public void accesrefusedfromtheuser() {

    if(logDao.accesrefusedfromtheuser() != null) {
        for (String fild : logDao.accesrefusedfromtheuser().keySet() ){
            System.out.println("user: "+fild +"-> actions refused: "+logDao.accesrefusedfromtheuser().get(fild));
        }
    }
    

    }

    public void userwithmostactivites() {

    if(logDao.themost() != null){
        for(String name : logDao.themost().keySet()){
            System.out.println("the most user: "+name +" with :"+logDao.themost().get(name) + " actions");
        }
    }
   

    }

    public void actionsbytype() {

  if(logDao.byActiontype() != null){
        for(String action : logDao.byActiontype().keySet()){
            System.out.println("{ "+action +"="+logDao.byActiontype().get(action) + "}");
        }
    }
    }

}
