package net.ccs.android.model.kitchen;

/**
 * Allows to discard changes made to the picked meal component.
 * 
 * @author "Alexey Dmitriev"
 *
 */
public class MenuMealItemMemento {
	
	/** actual amount of meal served */
	private Double actualMealQty;

	/** meal ratio for this meal component */
	private Double mealRatio;

	/** picked meal component */
	private MealComponent mealComponent;
	
	/**
	 * @return the actualMealQty
	 */
	public Double getActualMealQty() {
		return actualMealQty;
	}

	/**
	 * @param actualMealQty the actualMealQty to set
	 */
	public void setActualMealQty(Double actualMealQty) {
		this.actualMealQty = actualMealQty;
	}

	/**
	 * @return the mealRatio
	 */
	public Double getMealRatio() {
		return mealRatio;
	}

	/**
	 * @param mealRatio the mealRatio to set
	 */
	public void setMealRatio(Double mealRatio) {
		this.mealRatio = mealRatio;
	}

	/**
	 * @return the mealComponent
	 */
	public MealComponent getMealComponent() {
		return mealComponent;
	}

	/**
	 * @param mealComponent the mealComponent to set
	 */
	public void setMealComponent(MealComponent mealComponent) {
		this.mealComponent = mealComponent;
	}
}
