public void enterValueInWheelPicker(IOSDriver driver, String value) {
		try {
		WebElement e = driver.findElement(By.id(value));
		click(e, value);
		} catch (Exception e) {
			driver.findElementByClassName("XCUIElementTypePickerWheel").sendKeys(value);
			LogManager.getLogger().info(value+" is sent to wheelpicker");
		}
	}

	public void enterValueInWheelPicker2(IOSDriver driver, String value) {
		try {
			HashMap<String, Object> params = new HashMap<>();
			params.put("order", "next");
			params.put("offset", 0.15);
			params.put("element", driver.findElementByClassName("XCUIElementTypePickerWheel"));
			driver.executeScript("mobile: selectPickerWheelValue", params);
		} catch (Exception e) {
			e.printStackTrace();
		}
