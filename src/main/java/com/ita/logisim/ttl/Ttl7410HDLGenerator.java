package com.ita.logisim.ttl;

import java.util.ArrayList;
import java.util.SortedMap;
import java.util.TreeMap;

import com.bfh.logisim.designrulecheck.Netlist;
import com.bfh.logisim.designrulecheck.NetlistComponent;
import com.bfh.logisim.fpgagui.FPGAReport;
import com.bfh.logisim.hdlgenerator.AbstractHDLGeneratorFactory;
import com.bfh.logisim.hdlgenerator.HDLGeneratorFactory;
import com.cburch.logisim.data.AttributeSet;

public class Ttl7410HDLGenerator extends AbstractHDLGeneratorFactory {

	private boolean Inverted = true;
	private boolean andgate = true;
	
	public Ttl7410HDLGenerator() {
		super();
	}

	public Ttl7410HDLGenerator(boolean invert,boolean IsAnd) {
		super();
		Inverted = invert;
		andgate = IsAnd;
	}
	
	@Override
	public String getComponentStringIdentifier() {
		return "TTL";
	}
	
	@Override
	public SortedMap<String, Integer> GetInputList(Netlist TheNetlist,
			AttributeSet attrs) {
		SortedMap<String, Integer> MyInputs = new TreeMap<String, Integer>();
		MyInputs.put("A0", 1);
		MyInputs.put("B0", 1);
		MyInputs.put("C0", 1);
		MyInputs.put("A1", 1);
		MyInputs.put("B1", 1);
		MyInputs.put("C1", 1);
		MyInputs.put("A2", 1);
		MyInputs.put("B2", 1);
		MyInputs.put("C2", 1);
		return MyInputs;
	}

	@Override
	public SortedMap<String, Integer> GetOutputList(Netlist TheNetlist,
			AttributeSet attrs) {
		SortedMap<String, Integer> MyOutputs = new TreeMap<String, Integer>();
		MyOutputs.put("Y0", 1);
		MyOutputs.put("Y1", 1);
		MyOutputs.put("Y2", 1);
		return MyOutputs;
	}

	@Override
	public ArrayList<String> GetModuleFunctionality(Netlist TheNetlist,
			AttributeSet attrs, FPGAReport Reporter, String HDLType) {
		ArrayList<String> Contents = new ArrayList<String>();
		String Inv = Inverted ? "NOT" : "" ;
		String Func = andgate ? "AND" : "OR";
		Contents.add("   Y0 <= "+Inv+" (A0 "+Func+" B0 "+Func+" C0);");
		Contents.add("   Y1 <= "+Inv+" (A1 "+Func+" B1 "+Func+" C1);");
		Contents.add("   Y2 <= "+Inv+" (A2 "+Func+" B2 "+Func+" C2);");
		return Contents;
	}

	@Override
	public SortedMap<String, String> GetPortMap(Netlist Nets,
			NetlistComponent ComponentInfo, FPGAReport Reporter, String HDLType) {
		SortedMap<String, String> PortMap = new TreeMap<String, String>();
		PortMap.putAll(GetNetMap("A0",true,ComponentInfo,0,Reporter,HDLType,Nets));
		PortMap.putAll(GetNetMap("B0",true,ComponentInfo,1,Reporter,HDLType,Nets));
		PortMap.putAll(GetNetMap("C0",true,ComponentInfo,11,Reporter,HDLType,Nets));
		PortMap.putAll(GetNetMap("Y0",true,ComponentInfo,10,Reporter,HDLType,Nets));
		PortMap.putAll(GetNetMap("A1",true,ComponentInfo,2,Reporter,HDLType,Nets));
		PortMap.putAll(GetNetMap("B1",true,ComponentInfo,3,Reporter,HDLType,Nets));
		PortMap.putAll(GetNetMap("C1",true,ComponentInfo,4,Reporter,HDLType,Nets));
		PortMap.putAll(GetNetMap("Y1",true,ComponentInfo,5,Reporter,HDLType,Nets));
		PortMap.putAll(GetNetMap("A2",true,ComponentInfo,9,Reporter,HDLType,Nets));
		PortMap.putAll(GetNetMap("B2",true,ComponentInfo,8,Reporter,HDLType,Nets));
		PortMap.putAll(GetNetMap("C2",true,ComponentInfo,7,Reporter,HDLType,Nets));
		PortMap.putAll(GetNetMap("Y2",true,ComponentInfo,6,Reporter,HDLType,Nets));
		return PortMap;
	}
	
	@Override
	public String GetSubDir() {
		/*
		 * this method returns the module directory where the HDL code needs to
		 * be placed
		 */
		return "ttl";
	}

	@Override
	public boolean HDLTargetSupported(String HDLType, AttributeSet attrs) {
		/* TODO: Add support for the ones with VCC and Ground Pin */
		if (attrs==null)
			return false;
		return (!attrs.getValue(TTL.VCC_GND)&&(HDLType.equals(HDLGeneratorFactory.VHDL)));
	}
}
