package de.hftstuttgart.projectindoorweb.web.internal.requests.positioning;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import de.hftstuttgart.projectindoorweb.web.internal.requests.project.SaveNewProjectParameters;

import java.util.Set;

public class GenerateBatchPositionResults {

    private Long buildingIdentifier;
    private Long evalFileIdentifier;
    private Long[] radioMapFileIdentifiers;
    private String algorithmType;
    private Set<SaveNewProjectParameters> saveNewProjectParamaters;
    private boolean withPixelPosition;


    @JsonCreator
    public GenerateBatchPositionResults(@JsonProperty("buildingIdentifier") Long buildingIdentifier,
                                        @JsonProperty("evaluationFile") Long evalFileIdentifier,
                                        @JsonProperty("radioMapFiles") Long[] radioMapFileIdentifiers,
                                        @JsonProperty("algorithmType") String algorithmType,
                                        @JsonProperty("projectParameters") Set<SaveNewProjectParameters> saveNewProjectParamaters,
                                        @JsonProperty("withPixelPosition") boolean withPixelPosition) {

        this.buildingIdentifier = buildingIdentifier;
        this.evalFileIdentifier = evalFileIdentifier;
        this.radioMapFileIdentifiers = radioMapFileIdentifiers;
        this.algorithmType = algorithmType;
        this.saveNewProjectParamaters = saveNewProjectParamaters;
        this.withPixelPosition = withPixelPosition;
    }

    public Long getBuildingIdentifier() {
        return buildingIdentifier;
    }

    public void setBuildingIdentifier(Long buildingIdentifier) {
        this.buildingIdentifier = buildingIdentifier;
    }

    public Long getEvalFileIdentifier() {
        return evalFileIdentifier;
    }

    public void setEvalFileIdentifier(Long evalFileIdentifier) {
        this.evalFileIdentifier = evalFileIdentifier;
    }

    public Long[] getRadioMapFileIdentifiers() {
        return radioMapFileIdentifiers;
    }

    public void setRadioMapFileIdentifiers(Long[] radioMapFileIdentifiers) {
        this.radioMapFileIdentifiers = radioMapFileIdentifiers;
    }

    public String getAlgorithmType() {
        return algorithmType;
    }

    public void setAlgorithmType(String algorithmType) {
        this.algorithmType = algorithmType;
    }

    public Set<SaveNewProjectParameters> getSaveNewProjectParamaters() {
        return saveNewProjectParamaters;
    }

    public void setSaveNewProjectParamaters(Set<SaveNewProjectParameters> saveNewProjectParamaters) {
        this.saveNewProjectParamaters = saveNewProjectParamaters;
    }

    public boolean isWithPixelPosition() {
        return withPixelPosition;
    }

    public void setWithPixelPosition(boolean withPixelPosition) {
        this.withPixelPosition = withPixelPosition;
    }
}