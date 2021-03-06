package de.hftstuttgart.projectindoorweb.web.internal.util;

public class TransmissionConstants { //TODO move to config container file and move somewhere more viewable

    public static final String GET_FLOOR_MAP_REST_URL = "/getFloorMap";

    public static final String EMPTY_STRING_VALUE = "";
    public static final String FALSE_STRING_VALUE = "false";

    public static final String WIFI_READING_PARAM = "wifiReading";

    public static final String POSITION_IDENTIFIER_PARAM = "positionIdentifier";
    public static final String PROJECT_IDENTIFIER_PARAM = "projectIdentifier";

    public static final String PROJECT_NAME_PARAM = "projectName";
    public static final String ALGORITHM_TYPE_PARAM = "algorithmType";
    public static final String ALGORITHM_IDENTIFIER_PARAM = "algorithmIdentifier";

    public static final String BUILDING_IDENTIFIER_PARAM = "buildingIdentifier";
    public static final String BUILDING_NAME_PARAM = "buildingName";

    public static final String NUMBER_OF_FLOORS_PARAM = "numberOfFloors";
    public static final String FLOOR_IDENTIFIER_PARAM = "floorIdentifier";
    public static final String FLOOR_NAME_PARAM = "floorName";
    public static final String FLOOR_MAP_FILE_PARAM = "floorMapFile";

    public static final String RADIOMAP_FILES_PARAM = "radioMapFiles";
    public static final String EVAL_FILE_PARAM = "evalFiles";
    public static final String EVAAL_FILE_IDENTIFIER_PARAM = "evaalFileIdentifier";
    public static final String WITH_PIXEL_POSITION_PARAM = "withPixelPosition";

    public static final String TRANSFORMED_POINTS_FILE_PARAM = "transformedPointsFiles";

    public static final String SAVE_NEW_PROJECT_NOTE = "Saves a new project in the database and returns its unique identifier. Returns -1 if save was not successful or given data was invalid.";
    public static final String SAVE_CURRENT_PROJECT_DATA_NOTE = "Overrides a given project with the given project data, based on the given project identifier. Returns true if successful, otherwise false.";
    public static final String SAVE_CURRENT_PROJECT_NOTE = "Overrides a given project with the given project, based on the given project identifier. Returns true if successful, otherwise false.";
    public static final String DELETE_PROJECT_NOTE = "Removes a given project from the database, based on the given project identifier. Returns true if successful, otherwise false.";
    public static final String LOAD_PROJECT_NOTE = "Loads and returns a wanted project, based on the given identifier. Returns an empty project if an error occurred or the given data was invalid.";
    public static final String GET_ALL_PROJECT_NOTE = "Returns all current projects in the database in a list or an empty list if no projects are available.";
    public static final String GET_ALL_ALGORITHMS_NOTE = "Returns all current algorithm types in a list that are currently available or an empty list if none are available.";
    public static final String GET_PARAMETERS_FOR_ALGORITHM_NOTE = "Returns a list of parameters which are currently associated with the given algorithm identifier. Returns an empty list if nothing has been found or if the given data is invalid.";
    public static final String GET_ALL_PARAMETERS_NOTE = "Returns a list of all parameters available to be specified in a Project.";

    public static final String GENERATE_RADIOMAPS_NOTE = "Generates a radio map. Returns true if generation was successful, otherwise false. Parameter transformed points file must not be null but can be empty";
    public static final String GENERATE_POSITIONRESULTS_NOTE = "Generates and returns position results. Returns a list of generated position results or an empty list if generation was not successful or given parameters were invalid.";
    public static final String GET_ALL_BUILDINGS_NOTE = "Returns a list of buildings or an empty list if no buildings are available.";
    public static final String GET_SINGLE_BUILDING_NOTE = "Returns detailed information about a building by its ID or null if a building with the specified ID does not exist.";
    public static final String UPDATE_BUILDING_NOTE = "Updates a given building by its ID. Returns 'true' if the operation was successful, 'false' otherwise";
    public static final String CALCULATE_POSITION_NOTE = "Calculates and returns a position for a wifi reading line. Returns an empty position if given data was invalid.";
    public static final String GET_POSITIONRESULTS_NOTE = "Returns a list of position results which are associated with a given project identifier.";
    public static final String GET_EVALUATIONENTRIES_NOTE = "Returns a list of evaluation entries which are associated with a given building identifier.";
    public static final String ADD_NEW_BUILDING_NOTE = "Adds a new building to the database and returns its unique identifier or -1 if given data was invalid.";
    public static final String ADD_FLOOR_TO_BUILDING_NOTE = "Adds a floor to a given building";
    public static final String DELETE_FLOOR_NOTE = "Removes a given floor from the database, based on the given floor identifier. Returns true if successful, otherwise false.";
    public static final String DELETE_BUILDING_NOTE = "Removes a given building from the database, based on the given building identifier. Returns true if successful, otherwise false.";
    public static final String DELETE_EVAAL_FILE_NOTE = "Removes a given evaal file from the database, based on the given evaal file identifier. Returns true if successful, otherwise false.";
    public static final String GET_ALL_EVAAL_FILES_NOTE = "Returns a list containing all available evaal files in the database or an empty list if nothing has been found.";
    public static final String GET_FLOOR_MAP_NOTE = "Returns an image which belongs to the given floor identifier. Returns NULL if given data was invalid or nothing has been found or an IOException was thrown";
}
