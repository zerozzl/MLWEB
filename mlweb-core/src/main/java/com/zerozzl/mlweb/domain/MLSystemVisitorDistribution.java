package com.zerozzl.mlweb.domain;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.zerozzl.mlweb.persistent.SystemVisitorDistribution;

public class MLSystemVisitorDistribution implements Serializable {

	public class Country implements Serializable {
		private static final long serialVersionUID = 5631502248855066125L;
		private String Name;
		private int Quantity;
		private Map<String, Province> Provinces;

		public Country(MLVisitor visitor) {
			this.Name = visitor.getCountry();
			this.Quantity = 1;
			this.Provinces = new HashMap<String, Province>();
			this.Provinces.put(visitor.getProvince(), new Province(visitor));
		}
		
		public Country(SystemVisitorDistribution distribution) {
			this.Name = distribution.getCountry();
			this.Quantity = distribution.getQuantity();
			this.Provinces = new HashMap<String, Province>();
			this.Provinces.put(distribution.getProvince(), new Province(distribution));
		}
		
		public String getName() {
			return Name;
		}

		public int getQuantity() {
			return Quantity;
		}

		public Map<String, Province> getProvinces() {
			return Provinces;
		}
		
		public void addVisitor(MLVisitor visitor) {
			if(visitor != null) {
				if(Provinces.containsKey(visitor.getProvince())) {
					Provinces.get(visitor.getProvince()).addVisitor(visitor);
				} else {
					Provinces.put(visitor.getProvince(), new Province(visitor));
				}
				this.Quantity += 1;
			}
		}
		
		public void addDistribution(SystemVisitorDistribution distribution) {
			if(distribution != null) {
				if(Provinces.containsKey(distribution.getProvince())) {
					Provinces.get(distribution.getProvince()).addDistribution(distribution);
				} else {
					Provinces.put(distribution.getProvince(), new Province(distribution));
				}
				this.Quantity += distribution.getQuantity();
			}
		}

	}

	public class Province implements Serializable {
		private static final long serialVersionUID = 3332207107199725848L;
		private String Name;
		private int Quantity;
		private Map<String, City> Cities;

		public Province(MLVisitor visitor) {
			this.Name = visitor.getProvince();
			this.Quantity = 1;
			this.Cities = new HashMap<String, City>();
			this.Cities.put(visitor.getCity(), new City(visitor));
		}
		
		public Province(SystemVisitorDistribution distribution) {
			this.Name = distribution.getProvince();
			this.Quantity = distribution.getQuantity();
			this.Cities = new HashMap<String, City>();
			this.Cities.put(distribution.getCity(), new City(distribution));
		}
		
		public String getName() {
			return Name;
		}

		public int getQuantity() {
			return Quantity;
		}
		
		public Map<String, City> getCities() {
			return Cities;
		}
		
		public void addVisitor(MLVisitor visitor) {
			if(visitor != null) {
				if(Cities.containsKey(visitor.getCity())) {
					Cities.get(visitor.getCity()).addVisitor();
				} else {
					Cities.put(visitor.getCity(), new City(visitor));
				}
				this.Quantity += 1;
			}
		}
		
		public void addDistribution(SystemVisitorDistribution distribution) {
			if(distribution != null) {
				if(Cities.containsKey(distribution.getCity())) {
					Cities.get(distribution.getCity()).addDistribution(distribution);
				} else {
					Cities.put(distribution.getCity(), new City(distribution));
				}
				this.Quantity += distribution.getQuantity();
			}
		}

	}

	public class City implements Serializable {
		private static final long serialVersionUID = -2830904703733177766L;
		private String Name;
		private int Quantity;

		public City(MLVisitor visitor) {
			this.Name = visitor.getCity();
			this.Quantity = 1;
		}
		
		public City(SystemVisitorDistribution distribution) {
			this.Name = distribution.getCity();
			this.Quantity = distribution.getQuantity();
		}
		
		public String getName() {
			return Name;
		}

		public int getQuantity() {
			return Quantity;
		}
		
		public void addVisitor() {
			this.Quantity += 1;
		}
		
		public void addDistribution(SystemVisitorDistribution distribution) {
			if(distribution != null) {
				this.Quantity += distribution.getQuantity();
			}
		}

	}

	private static final long serialVersionUID = -2175736261374971507L;
	private Map<String, Country> Countries;
	
	public MLSystemVisitorDistribution() {
		this.Countries = new HashMap<String, Country>();
	}
	
	public static MLSystemVisitorDistribution initByVisitors(List<MLVisitor> datas) {
		if(datas == null || datas.isEmpty()) {
			return null;
		}
		MLSystemVisitorDistribution distribution = new MLSystemVisitorDistribution();
		for(MLVisitor o : datas) {
			distribution.addVisitor(o);
		}
		return distribution;
	}
	
	private void addVisitor(MLVisitor visitor) {
		if(visitor != null) {
			if(Countries.containsKey(visitor.getCountry())) {
				Countries.get(visitor.getCountry()).addVisitor(visitor);
			} else {
				Countries.put(visitor.getCountry(), new Country(visitor));
			}
		}
	}
	
	public List<SystemVisitorDistribution> convertToDBModel(Date date) {
		List<SystemVisitorDistribution> distribution = new ArrayList<SystemVisitorDistribution>();
		for(Country c : Countries.values()) {
			for(Province p : c.getProvinces().values()) {
				for(City o : p.getCities().values()) {
					distribution.add(new SystemVisitorDistribution(
							date, c.getName(), p.getName(), o.getName(), o.getQuantity()));
				}
			}
		}
		return distribution;
	}
	
	public void convertFromDBModel(List<SystemVisitorDistribution> datas) {
		if(datas != null && !datas.isEmpty()) {
			for(SystemVisitorDistribution data : datas) {
				if(Countries.containsKey(data.getCountry())) {
					Countries.get(data.getCountry()).addDistribution(data);
				} else {
					Countries.put(data.getCountry(), new Country(data));
				}
			}
		}
	}

	public Map<String, Country> getCountries() {
		return Countries;
	}
	
}
