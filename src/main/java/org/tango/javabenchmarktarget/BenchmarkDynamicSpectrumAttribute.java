/*----- PROTECTED REGION ID(JavaBenchmarkTarget.BenchmarkDynamicSpectrumAttribute.java) ENABLED START -----*/
//=============================================================================
//
// file :        BenchmarkDynamicSpectrumAttribute.java
//
// description : Java source for the dynamic attribute BenchmarkDynamicSpectrumAttribute.
//               this attribute belongs to the JavaBenchmarkTarget class.
//
// project :     Benchmark device
//
// This file is part of Tango device class.
// 
// Tango is free software: you can redistribute it and/or modify
// it under the terms of the GNU General Public License as published by
// the Free Software Foundation, either version 3 of the License, or
// (at your option) any later version.
// 
// Tango is distributed in the hope that it will be useful,
// but WITHOUT ANY WARRANTY; without even the implied warranty of
// MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
// GNU General Public License for more details.
// 
// You should have received a copy of the GNU General Public License
// along with Tango.  If not, see <http://www.gnu.org/licenses/>.
// 
//
//
//=============================================================================
//                This file is generated by POGO
//        (Program Obviously used to Generate tango Object)
//=============================================================================

package org.tango.javabenchmarktarget;

import org.tango.server.StateMachineBehavior;
import org.tango.server.attribute.IAttributeBehavior;
import org.tango.server.attribute.AttributeValue;
import org.tango.server.attribute.AttributeConfiguration;
import org.tango.server.attribute.AttributePropertiesImpl;

import fr.esrf.Tango.*;

/*----- PROTECTED REGION END -----*/	//	JavaBenchmarkTarget.BenchmarkDynamicSpectrumAttribute.java


/**
 * Dynamic attribute BenchmarkDynamicSpectrumAttribute, double, Spectrum, READ_WRITE
 * description:
 *     dynamic spectrum attribute
 */
public class BenchmarkDynamicSpectrumAttribute implements IAttributeBehavior {

	/**	The attribute name */
	private String  attributeName;

	/*----- PROTECTED REGION ID(JavaBenchmarkTarget.BenchmarkDynamicSpectrumAttribute.dataMembers) ENABLED START -----*/
	
	//	Put your data member declarations

    double[] data = new double[1];

	/*----- PROTECTED REGION END -----*/	//	JavaBenchmarkTarget.BenchmarkDynamicSpectrumAttribute.dataMembers

	/**
	 * Dynamic attribute BenchmarkDynamicSpectrumAttribute constructor
	 * @param attributeName The dynamic attribute name
	 */
	public BenchmarkDynamicSpectrumAttribute(String attributeName) {
		this.attributeName = attributeName;
		/*----- PROTECTED REGION ID(JavaBenchmarkTarget.BenchmarkDynamicSpectrumAttribute.BenchmarkDynamicSpectrumAttribute) ENABLED START -----*/
		
		/*----- PROTECTED REGION END -----*/	//	JavaBenchmarkTarget.BenchmarkDynamicSpectrumAttribute.BenchmarkDynamicSpectrumAttribute
	}

	/**
	 * Build and return the configuration for dynamic attribute BenchmarkDynamicSpectrumAttribute.
	 * @return the configuration for dynamic attribute BenchmarkDynamicSpectrumAttribute.
	 * @throws DevFailed in case of configuration error.
	 */
	@Override
	public AttributeConfiguration getConfiguration() throws DevFailed {
		AttributeConfiguration  config = new AttributeConfiguration();
		config.setName(attributeName);
		config.setType(double.class);
		config.setFormat(AttrDataFormat.SPECTRUM);
		config.setWritable(AttrWriteType.READ_WRITE);
		config.setDispLevel(DispLevel.OPERATOR);
		config.setMaxX(4096);
	
		//	Set attribute properties
		AttributePropertiesImpl	properties = new AttributePropertiesImpl();
		properties.setDescription("dynamic spectrum attribute");
		config.setAttributeProperties(properties);
		return config;
	}

	/**
	 * Get dynamic attribute state machine
	 * @return the attribute state machine
	 * @throws DevFailed if the state machine computation failed.
	 */
	@Override
	public StateMachineBehavior getStateMachine() throws DevFailed {
		StateMachineBehavior stateMachine = new StateMachineBehavior();
		/*----- PROTECTED REGION ID(JavaBenchmarkTarget.BenchmarkDynamicSpectrumAttribute.getStateMachine) ENABLED START -----*/
		
		/*----- PROTECTED REGION END -----*/	//	JavaBenchmarkTarget.BenchmarkDynamicSpectrumAttribute.getStateMachine
		return stateMachine;
	}

	/**
	 * Get dynamic attribute value
	 * @return the attribute value
	 * @throws DevFailed if the read value failed.
	 */
	@Override
	public AttributeValue getValue() throws DevFailed {
		double[]	readValue;
		/*----- PROTECTED REGION ID(JavaBenchmarkTarget.BenchmarkDynamicSpectrumAttribute.getValue) ENABLED START -----*/

		readValue = data;

		/*----- PROTECTED REGION END -----*/	//	JavaBenchmarkTarget.BenchmarkDynamicSpectrumAttribute.getValue
		return new AttributeValue(readValue);
	}

	/**
	 * Set dynamic attribute value
	 * @param writeValue the attribute value
	 * @throws DevFailed if the set value failed.
	 */
	@Override
	public void setValue(AttributeValue writeValue) throws DevFailed {
		/*----- PROTECTED REGION ID(JavaBenchmarkTarget.BenchmarkDynamicSpectrumAttribute.setValue) ENABLED START -----*/

		data = (double[]) writeValue.getValue();

		/*----- PROTECTED REGION END -----*/	//	JavaBenchmarkTarget.BenchmarkDynamicSpectrumAttribute.setValue
	}

	/*----- PROTECTED REGION ID(JavaBenchmarkTarget.BenchmarkDynamicSpectrumAttribute.methods) ENABLED START -----*/
	
	//	Put your own methods

	public void setData(double[] newData) {
	    data = newData;
	}
	
	/*----- PROTECTED REGION END -----*/	//	JavaBenchmarkTarget.BenchmarkDynamicSpectrumAttribute.methods
}
