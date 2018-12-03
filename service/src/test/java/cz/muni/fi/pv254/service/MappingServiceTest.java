//package cz.muni.fi.pv254.service;
//
//import MappingService;
//import ServiceConfiguration;
//import cz.muni.fi.pv254.dto.MachineDTO;
//import cz.muni.fi.pv254.dto.RevisionDTO;
//import cz.muni.fi.pv254.entity.Machine;
//import cz.muni.fi.pv254.entity.Revision;
//
//import java.math.BigDecimal;
//import java.time.LocalDate;
//import java.time.Month;
//import java.util.ArrayList;
//import java.util.List;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.test.context.ContextConfiguration;
//import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
//import org.testng.Assert;
//import org.testng.annotations.BeforeMethod;
//import org.testng.annotations.Test;
//
//@ContextConfiguration(classes = ServiceConfiguration.class)
//public class MappingServiceTest extends AbstractTestNGSpringContextTests {
//    @Autowired
//    private MappingService mappingService;
//
//    private Revision revision1;
//    private RevisionDTO revisionDTO1;
//    private Machine machine1, machine2;
//    private MachineDTO machineDTO1, machineDTO2;
//    private List<Machine> machineList;
//    private List<MachineDTO> machineDTOList;
//
//    @BeforeMethod
//    public void init() {
//        machineList = new ArrayList<>();
//        machineDTOList = new ArrayList<>();
//
//        machine1 = new Machine();
//        machine1.setName("machine1");
//        machine1.setDescription("desc");
//        machine1.setCreatedDate(LocalDate.of(2015, Month.NOVEMBER, 20));
//        machine1.setPricePerDay(new BigDecimal("0.5"));
//        machineList.add(machine1);
//
//        machine2 = new Machine();
//        machine2.setName("machine2");
//        machine2.setDescription("desc2");
//        machine2.setCreatedDate(LocalDate.of(2014, Month.APRIL, 10));
//        machine2.setPricePerDay(new BigDecimal("0.8"));
//        machineList.add(machine2);
//
//        machineDTO1 = new MachineDTO();
//        machineDTO1.setName("machine1");
//        machineDTO1.setDescription("desc");
//        machineDTO1.setCreatedDate(LocalDate.of(2015, Month.NOVEMBER, 20));
//        machineDTO1.setPricePerDay(new BigDecimal("0.5"));
//        machineDTOList.add(machineDTO1);
//
//        machineDTO2 = new MachineDTO();
//        machineDTO2.setName("machine2");
//        machineDTO2.setDescription("desc2");
//        machineDTO2.setCreatedDate(LocalDate.of(2014, Month.APRIL, 10));
//        machineDTO2.setPricePerDay(new BigDecimal("0.8"));
//        machineDTOList.add(machineDTO2);
//
//        revision1 = new Revision();
//        revision1.setDate(LocalDate.of(2013, Month.SEPTEMBER, 15));
//        revision1.setMachine(machine1);
//
//        revisionDTO1 = new RevisionDTO();
//        revisionDTO1.setDate(LocalDate.of(2013, Month.SEPTEMBER, 15));
//        revisionDTO1.setMachine(machineDTO1);
//    }
//
//    @Test
//    public void testDTOToEntityList() {
//        List<Machine> result = mappingService.mapTo(machineDTOList, Machine.class);
//        Assert.assertEquals(result.get(0), machine1);
//        Assert.assertEquals(result.get(1), machine2);
//    }
//
//    @Test
//    public void testEntityToDTOList() {
//        List<MachineDTO> result = mappingService.mapTo(machineList, MachineDTO.class);
//        Assert.assertEquals(result.get(0), machineDTO1);
//        Assert.assertEquals(result.get(1), machineDTO2);
//    }
//
//    @Test
//    public void testDTOToEntity() {
//        Machine result = mappingService.mapTo(machineDTO1, Machine.class);
//        Assert.assertNotNull(result);
//        Assert.assertEquals(result, machine1);
//    }
//
//    @Test
//    public void testEntityToDTO() {
//        MachineDTO result = mappingService.mapTo(machine1, MachineDTO.class);
//        Assert.assertNotNull(result);
//        Assert.assertEquals(result, machineDTO1);
//    }
//
//    @Test
//    public void testDTOToEntity1() {
//        Revision result = mappingService.mapTo(revisionDTO1, Revision.class);
//        Assert.assertNotNull(result);
//        Assert.assertEquals(result, revision1);
//    }
//
//    @Test
//    public void testEntityToDTO1() {
//        RevisionDTO result = mappingService.mapTo(revision1, RevisionDTO.class);
//        Assert.assertNotNull(result);
//        Assert.assertEquals(result, revisionDTO1);
//    }
//}