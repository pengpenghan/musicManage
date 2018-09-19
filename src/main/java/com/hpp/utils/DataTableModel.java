package com.hpp.utils;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class DataTableModel<T> implements Serializable {
	private static final long serialVersionUID = 1L;
	private List<T> data = new ArrayList<T>();
    private int draw;
	private int recordsFiltered;
	private int recordsTotal;

    public DataTableModel() {
    }

    public DataTableModel(List<T> data) {
        this.data = data;
    }
    
    public DataTableModel(List<T> data, int recordsFiltered, int recordsTotal) {
		super();
		this.data = data;
		this.recordsFiltered = recordsFiltered;
		this.recordsTotal = recordsTotal;
	}

	public List<T> getData() {
        return data;
    }

    public void setData(List<T> data) {
        this.data = data;
    }

	public int getDraw() {
		return draw;
	}

	public void setDraw(int draw) {
		this.draw = draw;
	}

	public int getRecordsFiltered() {
		return recordsFiltered;
	}

	public void setRecordsFiltered(int recordsFiltered) {
		this.recordsFiltered = recordsFiltered;
	}

	public int getRecordsTotal() {
		return recordsTotal;
	}

	public void setRecordsTotal(int recordsTotal) {
		this.recordsTotal = recordsTotal;
	}
    
}
