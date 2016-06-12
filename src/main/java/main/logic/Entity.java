package main.logic;

import main.database.AbstractDataMapper;

import java.sql.SQLException;

/**
 * Created by oglandx on 5/8/16.
 */
public class Entity {
    public int getId() {
        return id;
    }

    protected void setId(int id){
        this.id = id;
    }

    private int id;

    private AbstractDataMapper dataMapper;

    public AbstractDataMapper getDataMapper(){
        return dataMapper;
    }

    public void setDataMapper(AbstractDataMapper dataMapper){
        this.dataMapper = dataMapper;
    }

    public boolean save() throws SQLException {
        if (getDataMapper() == null){
            return false;
        }
        try {
            getDataMapper().update(this);
        }
        catch (SQLException e){
            getDataMapper().insert(this);
        }
        return true;
    }
}
