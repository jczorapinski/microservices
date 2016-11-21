package microservices.io;

import java.util.*;
import javax.usb.*;

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

@Profile("sudo")
@Component
public class UsbService {

	public UsbService() throws Exception {
		UsbServices services = UsbHostManager.getUsbServices();
		UsbHub root = services.getRootUsbHub();
		printAttachedDevices(root);
	}

	@SuppressWarnings("rawtypes")
	public void printAttachedDevices(UsbHub hub) throws Exception {
		List devices = hub.getAttachedUsbDevices();
		Iterator iterator = devices.iterator();

		while (iterator.hasNext()) {
			UsbDevice device = (UsbDevice) iterator.next();
			if (device.isUsbHub()) {
				printAttachedDevices((UsbHub) device);
			} else
				System.out.println("[" + device.getManufacturerString() + "] {" + device.getProductString() + "} ("
						+ device.getSerialNumberString() + ")");
		}
	}
}