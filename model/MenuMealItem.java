package net.ccs.android.model.kitchen;

import net.ccs.android.model.EnumerationValue;
import net.ccs.android.model.ServerEntity;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

/**
CREATE TABLE `menu_meal_item` (
  `uid` INTEGER PRIMARY KEY AUTOINCREMENT,
  `id_food_group` bigint(20) NOT NULL,
  `description` varchar(100) DEFAULT NULL,
  `id_meal_component` bigint(20) NOT NULL,
  `id_meal_fraction` bigint(20) DEFAULT NULL,
  `meal_ratio` double NOT NULL,
  `estimated_meal_qty` double DEFAULT NULL,
  `secondary_sort_order` int(11) DEFAULT NULL,
  `id_menu_meal` bigint(20) DEFAULT NULL,
  `id_menu_template_meal_item` bigint(20) NOT NULL,
  `actual_meal_qty` double DEFAULT NULL,
  FOREIGN KEY (`id_meal_component`) REFERENCES `meal_component` (`uid`),
  FOREIGN KEY (`id_food_group`) REFERENCES `enumeration_value` (`uid`),
  FOREIGN KEY (`id_meal_fraction`) REFERENCES `enumeration_value` (`uid`),
  FOREIGN KEY (`id_menu_meal`) REFERENCES `menu_meal` (`uid`)
);
 */

@DatabaseTable(tableName = "menu_meal_item")
public class MenuMealItem extends ServerEntity {

	@DatabaseField(foreign = true, columnName = "id_menu_meal")
	private MenuMeal menuMeal;
	
	@DatabaseField(foreign = true, 
			foreignAutoRefresh = true, 
			columnName = "id_menu_template_meal_item", 
			maxForeignAutoRefreshLevel = 5)
	private MenuTemplateMealItem mealTemplateItem;

	@DatabaseField(columnName = "estimated_meal_qty")
	private Double estimatedMealQty;

	@DatabaseField(columnName = "actual_meal_qty")
	private Double actualMealQty;

	@DatabaseField
	private String description;

	@Deprecated
	@DatabaseField(foreign = true, columnName = "id_meal_fraction")
	private EnumerationValue mealFraction; // Enumeration: MealFraction

	@DatabaseField(columnName = "meal_ratio")
	private Double mealRatio;

	@DatabaseField(foreign = true, foreignAutoRefresh = true, columnName = "id_food_group")
	private EnumerationValue foodGroup; // Enumeration: FoodGroup

	@DatabaseField(foreign = true, foreignAutoRefresh = true, columnName = "id_meal_component")
	private MealComponent mealComponent;

	@DatabaseField(columnName = "secondary_sort_order")
	private Integer secondarySortOrder;
	
	// used for Pick Meal Component discrimination
	private boolean primary;

	///////////////////////////////////////////////////////////////////////////

	/**
	 * Default constructor
	 */
	public MenuMealItem() {
		this.mealRatio = 0.0;
	}
	
	/**
	 * Saves meal item state.
	 */
	public MenuMealItemMemento toMemento() {
		MenuMealItemMemento result = new MenuMealItemMemento();
		result.setActualMealQty(actualMealQty);
		result.setMealComponent(mealComponent);
		result.setMealRatio(mealRatio);
		
		return result;
	}
	
	/**
	 * Restores meal item state.
	 */
	public void fromMemento(MenuMealItemMemento memento) {
		actualMealQty = memento.getActualMealQty();
		mealComponent = memento.getMealComponent();
		mealRatio = memento.getMealRatio();
	}
	
	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result
				+ ((actualMealQty == null) ? 0 : actualMealQty.hashCode());
		result = prime * result
				+ ((description == null) ? 0 : description.hashCode());
		result = prime
				* result
				+ ((estimatedMealQty == null) ? 0 : estimatedMealQty.hashCode());
		result = prime * result
				+ ((foodGroup == null) ? 0 : foodGroup.hashCode());
		result = prime * result
				+ ((mealComponent == null) ? 0 : mealComponent.hashCode());
		result = prime * result
				+ ((mealRatio == null) ? 0 : mealRatio.hashCode());
		result = prime
				* result
				+ ((mealTemplateItem == null) ? 0 : mealTemplateItem.hashCode());
		result = prime * result
				+ ((menuMeal == null) ? 0 : menuMeal.hashCode());
		result = prime * result + (primary ? 1231 : 1237);
		result = prime
				* result
				+ ((secondarySortOrder == null) ? 0 : secondarySortOrder
						.hashCode());
		return result;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (!(obj instanceof MenuMealItem))
			return false;
		MenuMealItem other = (MenuMealItem) obj;
		if (actualMealQty == null) {
			if (other.actualMealQty != null)
				return false;
		} else if (!actualMealQty.equals(other.actualMealQty))
			return false;
		if (description == null) {
			if (other.description != null)
				return false;
		} else if (!description.equals(other.description))
			return false;
		if (estimatedMealQty == null) {
			if (other.estimatedMealQty != null)
				return false;
		} else if (!estimatedMealQty.equals(other.estimatedMealQty))
			return false;
		if (foodGroup == null) {
			if (other.foodGroup != null)
				return false;
		} else if (!foodGroup.equals(other.foodGroup))
			return false;
		if (mealComponent == null) {
			if (other.mealComponent != null)
				return false;
		} else if (!mealComponent.equals(other.mealComponent))
			return false;
		if (mealRatio == null) {
			if (other.mealRatio != null)
				return false;
		} else if (!mealRatio.equals(other.mealRatio))
			return false;
		if (mealTemplateItem == null) {
			if (other.mealTemplateItem != null)
				return false;
		} else if (!mealTemplateItem.equals(other.mealTemplateItem))
			return false;
		if (menuMeal == null) {
			if (other.menuMeal != null)
				return false;
		} else if (!menuMeal.equals(other.menuMeal))
			return false;
		if (primary != other.primary)
			return false;
		if (secondarySortOrder == null) {
			if (other.secondarySortOrder != null)
				return false;
		} else if (!secondarySortOrder.equals(other.secondarySortOrder))
			return false;
		return true;
	}

	/**
	 * @return the mealTemplate
	 * @hibernate.many-to-one column="id_menu_template_meal_item" not-null="true" lazy="proxy"
	 */
	public MenuTemplateMealItem getMealTemplateItem() {
		return this.mealTemplateItem;
	}

	/**
	 * @param mealTemplateItem the mealTemplateItem to set
	 */
	public void setMealTemplateItem(MenuTemplateMealItem mealTemplateItem) {
		this.mealTemplateItem = mealTemplateItem;
	}

	/**
	 * @return Returns the foodGroup.
	 * @hibernate.many-to-one column="id_food_group" not-null="true" lazy="false"
	 */
	public EnumerationValue getFoodGroup() {
		return this.foodGroup;
	}
	/**
	 * @param foodGroup The foodGroup to set.
	 */
	public void setFoodGroup(EnumerationValue foodGroup) {
		this.foodGroup = foodGroup;
	}

	/**
	 * @return Returns the description.
	 * @hibernate.property 
	 * @hibernate.column name="description" not-null="false" unique="false" length="100"
	 */
	public String getDescription() {
		return this.description;
	}
	/**
	 * @param description The description to set.
	 */
	public void setDescription(String description) {
		this.description = description;
	}

	/**
	 * @return Returns the mealComponent.
	 * @hibernate.many-to-one column="id_meal_component" not-null="true" 
	 */
	public MealComponent getMealComponent() {
		return this.mealComponent;
	}
	/**
	 * @param mealComponent The mealComponent to set.
	 */
	public void setMealComponent(MealComponent mealComponent) {
		this.mealComponent = mealComponent;
	}

	/**
	 * @return Returns the mealFraction.
	 * @hibernate.many-to-one column="id_meal_fraction" not-null="false"
	 */
	@Deprecated
	public EnumerationValue getMealFraction() {
		return this.mealFraction;
	}
	/**
	 * @param mealFraction The mealFraction to set.
	 */
	@Deprecated
	public void setMealFraction(EnumerationValue mealFraction) {
		this.mealFraction = mealFraction;
	}

	/**
	 * @return the mealRatio
	 * @hibernate.property 
	 * @hibernate.column name="meal_ratio" not-null="true" unique="false"
	 */
	public Double getMealRatio() {
		return this.mealRatio;
	}

	/**
	 * @param mealRatio the mealRatio to set
	 */
	public void setMealRatio(Double mealRatio) {
		this.mealRatio = mealRatio;
	}

	/**
	 * @return Returns the estimatedMealQty.
	 * @hibernate.property 
	 * @hibernate.column name="estimated_meal_qty" not-null="false" unique="false" 
	 */
	public Double getEstimatedMealQty() {
		return this.estimatedMealQty;
	}
	/**
	 * @param estimatedMealQty The estimatedMealQty to set.
	 */
	public void setEstimatedMealQty(Double estimatedMealQty) {
		this.estimatedMealQty = estimatedMealQty;
	}

	/**
	 * @return Returns the actualMealQty.
	 * @hibernate.property 
	 * @hibernate.column name="actual_meal_qty" not-null="false" unique="false" 
	 */
	public Double getActualMealQty() {
		return this.actualMealQty;
	}
	/**
	 * @param actualMealQty The actualMealQty to set.
	 */
	public void setActualMealQty(Double actualMealQty) {
		this.actualMealQty = actualMealQty;
	}

	/**
	 * @return Returns the secondarySortOrder.
	 * @hibernate.property 
	 * @hibernate.column name="secondary_sort_order" not-null="false" unique="false" 
	 */
	public Integer getSecondarySortOrder() {
		return this.secondarySortOrder;
	}
	/**
	 * @param secondarySortOrder The secondarySortOrder to set.
	 */
	public void setSecondarySortOrder(Integer secondarySortOrder) {
		this.secondarySortOrder = secondarySortOrder;
	}

	public MenuMeal getMenuMeal() {
		return menuMeal;
	}

	public void setMenuMeal(MenuMeal menuMeal) {
		this.menuMeal = menuMeal;
	}

	/**
	 * @return the primary
	 */
	public boolean isPrimary() {
		return primary;
	}

	/**
	 * @param primary the primary to set
	 */
	public void setPrimary(boolean primary) {
		this.primary = primary;
	}
}
