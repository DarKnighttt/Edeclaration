package com.projects.darknight.edeclaration.database;

import com.projects.darknight.edeclaration.pojo.Worker;

import java.util.List;

public interface IDatabaseHelper {

    List<Worker> getAllWorkers();
    Worker getWorker(int id);
    void addWorker(Worker worker);
    void deleteWorker(Worker worker);
    void deleteAll();

}
