package de.hftstuttgart.projectindoorweb.web;

import de.hftstuttgart.projectindoorweb.application.internal.AssertParam;
import de.hftstuttgart.projectindoorweb.inputHandler.PreProcessingService;
import de.hftstuttgart.projectindoorweb.inputHandler.PreProcessingServiceComponent;
import de.hftstuttgart.projectindoorweb.persistence.PersistencyService;
import de.hftstuttgart.projectindoorweb.persistence.PersistencyServiceComponent;
import de.hftstuttgart.projectindoorweb.persistence.entities.*;
import de.hftstuttgart.projectindoorweb.persistence.repositories.LogFileRepository;
import de.hftstuttgart.projectindoorweb.positionCalculator.PositionCalculatorComponent;
import de.hftstuttgart.projectindoorweb.positionCalculator.PositionCalculatorService;
import de.hftstuttgart.projectindoorweb.web.internal.*;
import de.hftstuttgart.projectindoorweb.web.internal.util.EvaluationEntry;
import de.hftstuttgart.projectindoorweb.web.internal.util.TransmissionHelper;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.*;

public class RestTransmissionServiceImpl implements RestTransmissionService {

    private PersistencyService persistencyService;
    private PreProcessingService preProcessingService;
    private PositionCalculatorService positionCalculatorService;

    public RestTransmissionServiceImpl(PersistencyService persistencyService, PreProcessingService preProcessingService
            , PositionCalculatorService positionCalculatorService) {
        this.persistencyService = persistencyService;
        this.preProcessingService = preProcessingService;
        this.positionCalculatorService = positionCalculatorService;
    }

    @Override
    public boolean generateRadioMap(String projectIdentifier, String buildingIdentifier, MultipartFile[] radioMapFiles) {

        if (projectIdentifier == null || projectIdentifier.isEmpty()
                || buildingIdentifier == null || buildingIdentifier.isEmpty()
                || radioMapFiles == null || radioMapFiles.length == 0) {
            return false;
        }

        File[] radioMapFileArray = new File[radioMapFiles.length];

        try {
            for (int i = 0; i < radioMapFiles.length; i++) {
                radioMapFileArray[i] = TransmissionHelper.convertMultipartFileToLocalFile(radioMapFiles[i]);
            }
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }

        try {
            long projectId = Long.parseLong(projectIdentifier);
            Project project = this.persistencyService.getProjectById(projectId);
            List<LogFile> processedLogFiles = this.preProcessingService.processIntoLogFiles(project, radioMapFileArray);

            return this.persistencyService.saveLogFiles(processedLogFiles);
        } catch (NumberFormatException ex) {
            return false;
        }


    }

    @Override
    public List<CalculatedPosition> generatePositionResults(String projectIdentifier, String buildingIdentifier, MultipartFile evaluationFile) {

        List<CalculatedPosition> result = new ArrayList<>();
        if (AssertParam.isNullOrEmpty(projectIdentifier) || AssertParam.isNullOrEmpty(buildingIdentifier)
                || evaluationFile == null) {
            return result;
        }

        try {

            long projectId = Long.parseLong(projectIdentifier);
            Project project = this.persistencyService.getProjectById(projectId);

            File convertedEvaluationFile = TransmissionHelper.convertMultipartFileToLocalFile(evaluationFile);

            List<WifiPositionResult> retrievedWifiResults =
                    (List<WifiPositionResult>) this.positionCalculatorService.calculatePositions(convertedEvaluationFile, project);
            result = convertToCalculatedPositions(retrievedWifiResults);
            return result;

        } catch (NumberFormatException | IOException ex) {
            ex.printStackTrace();
            return result;
        }

    }

    @Override
    public CalculatedPosition getPositionForWifiReading(String wifiReading) {

        if (AssertParam.isNullOrEmpty(wifiReading)) {
            return createEmptyCalculatedPosition();
        }

        return new CalculatedPosition(0, 0, 0, false, ""); //TODO implement when ready
    }

    @Override
    public List<CalculatedPosition> getPositionResultsForProjectIdentifier(String projectIdentifier) {

        List<CalculatedPosition> result = new ArrayList<>();

        if (AssertParam.isNullOrEmpty(projectIdentifier)) {
            return result;
        }

        return result;//TODO implement when ready
    }

    @Override
    public long saveNewProject(Set<ProjectParameter> projectParameterSet, String projectName, String algorithmType) {

        if (projectParameterSet == null
                || projectParameterSet.size() == 0
                || AssertParam.isNullOrEmpty(projectName)
                || AssertParam.isNullOrEmpty(algorithmType)) {
            return -1;
        }

        return this.persistencyService.createNewProject(projectName, algorithmType, projectParameterSet);

    }

    @Override
    public boolean saveCurrentProject(String projectName, Set<ProjectParameter> projectParameterSet, String projectIdentifier, String algorithmType) {


        if (AssertParam.isNullOrEmpty(projectName) || projectParameterSet == null ||
                projectParameterSet.size() == 0 || AssertParam.isNullOrEmpty(projectIdentifier)
                || AssertParam.isNullOrEmpty(algorithmType)) {
            return false;
        }

        try {
            long projectId = Long.parseLong(projectIdentifier);
            return this.persistencyService.updateProject(projectId, projectName, algorithmType, projectParameterSet);
        } catch (NumberFormatException ex) {
            return false;
        }

    }

    @Override
    public boolean deleteSelectedProject(String projectIdentifier) {

        if (AssertParam.isNullOrEmpty(projectIdentifier)) {
            return false;
        }

        try {
            long projectId = Long.parseLong(projectIdentifier);
            return this.persistencyService.deleteProject(projectId);
        } catch (NumberFormatException ex) {
            return false;
        }

    }

    @Override
    public ProjectElement loadSelectedProject(String projectIdentifier) {

        if (AssertParam.isNullOrEmpty(projectIdentifier)) {
            return createEmptyProjectElement();
        }

        try {
            long projectId = Long.parseLong(projectIdentifier);
            Project project = this.persistencyService.getProjectById(projectId);

            if (project != null) {
                return new ProjectElement(project.getProjectName(), String.valueOf(project.getId()),
                        getProjectParametersFromInternalEntity(project.getProjectParameters()));
            }

        } catch (NumberFormatException ex) {
            ex.printStackTrace();
        }

        return createEmptyProjectElement();
    }

    @Override
    public List<ProjectElement> getAllProjects() {

        List<Project> projects = this.persistencyService.getAllProjects();

        return convertToProjectElements(projects);

    }

    @Override
    public List<BuildingElement> getAllBuildings() {
        List<Building> buildings = this.persistencyService.getAllBuildings();

        return convertToBuildingElements(buildings);
    }

    @Override
    public List<AlgorithmType> getAllAlgorithmTypes() {//TODO use reflection instead if viable
        List<AlgorithmType> result= new ArrayList<>();

        AlgorithmType wifiAlgorithm= new AlgorithmType("WifiPositionCalculatorServiceImpl", "Wifi");

        result.add(wifiAlgorithm);

        return result;
    }

    @Override
    public List<EvaluationEntry> getEvaluationEntriesForBuildingId(String buildingIdentifier) {
        List<EvaluationEntry> result = new ArrayList<>();
        if (AssertParam.isNullOrEmpty(buildingIdentifier)) {
            return result;
        }
        return result;//TODO implement when ready
    }

    @Override
    public List<ParameterElement> getAlgorithmParameterListForAlgorithmId(String algorithmIdentifier) {
        List<ParameterElement> result = new ArrayList<>();
        if (AssertParam.isNullOrEmpty(algorithmIdentifier)) {
            return result;
        }
        return result;//TODO implement when ready
    }

    @Override
    public long addNewBuilding(String buildingName, String numberOfFloors, PositionAnchor southEastAnchor, PositionAnchor southWestAnchor, PositionAnchor northEastAnchor, PositionAnchor northWestAnchor) {
        if (AssertParam.isNullOrEmpty(buildingName)
            || AssertParam.isNullOrEmpty(numberOfFloors)
            || southEastAnchor==null
            || southWestAnchor==null
            || northEastAnchor==null
            || northWestAnchor==null) {
            return -1;
        }
        try {
            long actualNumberOfFloors = Long.parseLong(numberOfFloors);
            return this.persistencyService.addNewBuilding( buildingName, actualNumberOfFloors, southEastAnchor,  southWestAnchor,  northEastAnchor, northWestAnchor);
        } catch (NumberFormatException ex) {
            return -1;
        }
    }

    private CalculatedPosition createEmptyCalculatedPosition() {
        return new CalculatedPosition(0, 0, 0, false, "");
    }

    private ProjectElement createEmptyProjectElement() {
        return new ProjectElement("", "", new HashSet<>());
    }

    private List<ProjectElement> convertToProjectElements(List<Project> projects) {

        List<ProjectElement> result = new ArrayList<>(projects.size());

        for (Project project :
                projects) {
            result.add(new ProjectElement(project.getProjectName(), String.valueOf(project.getId()),
                    getProjectParametersFromInternalEntity(project.getProjectParameters())));
        }

        return result;


    }

    private Set<ProjectParameter> getProjectParametersFromInternalEntity(List<Parameter> parameters) {

        Set<ProjectParameter> projectParameters = new LinkedHashSet<>();

        for (Parameter parameter :
                parameters) {
            projectParameters.add(new ProjectParameter(parameter.getParameterName(), parameter.getParamenterValue()));
        }

        return projectParameters;


    }

    private List<CalculatedPosition> convertToCalculatedPositions(List<? extends PositionResult> positionResults) {


        List<CalculatedPosition> result = new ArrayList<>(positionResults.size());

        for (PositionResult positionResult :
                positionResults) {
            result.add(new CalculatedPosition(positionResult.getX(), positionResult.getY(), positionResult.getZ(), positionResult.isWgs84(), "To be implemented"));
        }

        return result;

    }

    private List<BuildingElement> convertToBuildingElements(List<Building> buildings) {
        List<BuildingElement> result = new ArrayList<>(buildings.size());

        for (Building building :
                buildings) {
            result.add(new BuildingElement(building.getId(), building.getBuildingName(), building.getBuildingFloors().size()));
        }

        return result;
    }


}