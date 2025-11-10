package User;

import java.util.*;

public class Place 
{
    ArrayList <String> visitTypeInThisPlace = new ArrayList<>();
	String placeName;
	
	//COSTRUTTORE
	public Place (String placeName)
	{
		this.placeName = placeName;
	}
	
	public Set<String> get_type_visit_list ()
	{
		return new HashSet<>(visitTypeInThisPlace);
	}

	public void addVisitType (String visitType)
	{
		visitTypeInThisPlace.add(visitType);
	}

	public void stampTypeVisit()
	{
		Set<String> visitType = new HashSet<>(visitTypeInThisPlace);
		for (String type: visitType)
		{
			System.out.println (type);
		}
	}
	public String getPlaceName ()
	{
		return placeName;
	}
	   
}
