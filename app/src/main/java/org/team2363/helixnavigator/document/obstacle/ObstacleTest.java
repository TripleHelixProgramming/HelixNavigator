package org.team2363.helixnavigator.document.obstacle;

import com.jlbabilino.json.JSON;
import com.jlbabilino.json.JSONDeserializer;
import com.jlbabilino.json.JSONSerializer;

public class ObstacleTest {
    public static void main(String[] args) throws Exception {

        HObstacle obstacle = new HCircleObstacle();
        JSON json = JSONSerializer.serializeJSON(obstacle);
        System.out.println("original:");
        System.out.println(json);
        HObstacle newObstacle = JSONDeserializer.deserializeJSON(json, HObstacle.class);
        JSON newJSON = JSONSerializer.serializeJSON(newObstacle);
        System.out.println("new:");
        System.out.println(newJSON);
    }
}