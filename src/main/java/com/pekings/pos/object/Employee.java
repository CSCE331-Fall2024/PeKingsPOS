package com.pekings.pos.object;

import java.sql.Date;

public class Employee {

    private final long id;
    private final String username;
    private final String password;
    private final int totalHoursWorked;
    private final Date lastClockIn;
    private final boolean clockedIn;

    public Employee(long id, String username, String password, int totalHoursWorked, Date lastClockIn, boolean clockedIn) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.totalHoursWorked = totalHoursWorked;
        this.lastClockIn = lastClockIn;
        this.clockedIn = clockedIn;
    }

    public long getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public int getTotalHoursWorked() {
        return totalHoursWorked;
    }

    public Date getLastClockIn() {
        return lastClockIn;
    }

    public boolean isClockedIn() {
        return clockedIn;
    }
}
