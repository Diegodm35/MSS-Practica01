package classification;

import java.util.ArrayList;
import data.*;

public class KNN {
	// Atributos
	Environment neighbours;
	Distance distance_;

	// Constructor
	public KNN() {
		neighbours = new Environment();
		distance_ = new Distance_Euclidean();
	}

	public KNN(int dist) {
		switch(dist) {
		case 1:
			this.distance_ = new Distance_Euclidean();
			break;
		default:
			this.distance_ = new Distance_Euclidean();
			break;
		}
		neighbours = new Environment();
	}
	// Metodos
	public String predictType(Dataset data_, int k, Instance ins) {
		/*
		 * Buscar los k casos de data_ m�s cercanos a ins y almacenarlos en neighbourds
		 */
		ArrayList<Double> distancesToIns = new ArrayList<Double>();
		int numInstances_ = data_.getNumInstances();
		double tempDouble;
		/*
		 * calcular la distancia de todos los puntos a ins y guardarla en un array
		 */
		for(int i = 0; i < numInstances_; i++) {
			tempDouble = distance_.calculateDistance(data_.getInstance(i), ins.GetParams());
			distancesToIns.add(tempDouble);
		}
		/*
		 * Seleccionar los k puntos mas cercanos en nearestNeighbours
		 */
		ArrayList<Integer> nearestNeighbours = new ArrayList<Integer>(); 
		for(int i = 0; i < k; i++){
            int max = 0;
            for(int j = 0; j < distancesToIns.size(); j++){
                if(distancesToIns.get(j) > distancesToIns.get(max)){
                    max = j;
                }
            }
            // guarda los indices de las instancias m�s cercanas a ins
            nearestNeighbours.add(max);
            distancesToIns.remove(max);
        }		
		/* 
		 * Hacer un conteo de las clases dentro de este conjunto y asignar al nuevo dato
		 * la clase m�s repetida
		 */
		int count, maxCount = 0;
		ArrayList<String> types = data_.getTypes();
		String output = new String();
		for(int i = 0; i < types.size(); i++) {
			count = 0;
			for(int j = 0; j < k;j++) {
				// tempStr guarda el tipo del valor con el indice guardado en nearestNeighbours
				String tempStr = data_.getType(nearestNeighbours.get(j));
				if(types.get(i).equals(tempStr)) count++;
			}
			if(count >= maxCount) {
				maxCount = count;
				output = types.get(i);
			}
		}
		return output;
	}
}
