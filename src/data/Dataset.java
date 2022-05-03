package data;

import java.io.*;
import java.util.*;

public class Dataset {
	/*
	 * Atributos
	 */
	public static final String DELIMETER = ",";
	// cada espacio del array es una columna del fichero csv
	ArrayList<Attribute> attributes;
	// atributos normalizados
	ArrayList<Attribute> normalizedAttributes;
	// guarda los distintos tipos de flores
	ArrayList<String> types;
	//
	String titulo;
	int numAttributes;
	int numInstances;

	/*
	 * Constructor
	 */
	public Dataset() {
		attributes = new ArrayList<Attribute>();
		normalizedAttributes = new ArrayList<Attribute>();
		types = new ArrayList<String>();
		titulo = "";
		numAttributes = 0;
		numInstances = 0;
	}

	public Dataset(String filename) {
		read(filename);
	}

	/*
	 * Metodos
	 */
	public String getTitulo() {
		return titulo;
	}

	public int getNumAttributes() {
		return numAttributes;
	}

	public int getNumInstances() {
		return numInstances;
	}

	public ArrayList<String> getTypes() {
		return types;
	}
	
	public String getType(int i) {
		return (String) normalizedAttributes.get(numAttributes-1).getValue_(i);
	}

	public ArrayList<Double> getInstance(int ins) {
		ArrayList<Double> output = new ArrayList<Double>();
		for (int i = 0; i < numAttributes - 1; i++) {
			output.add((Double) normalizedAttributes.get(i).value_.get(ins));
		}
		return output;
	}

	public void normalize() {
		for (int i = 0; i < numAttributes - 1; i++) {
			normalizedAttributes.get(i).Normalize();
		}
	}

	public ArrayList<Double> normalizeIns(ArrayList<Double> ins) {
		double tempDouble;
		for (int i = 0; i < numAttributes - 1; i++) {
			tempDouble = ins.get(i);
			ins.set(i, normalizedAttributes.get(i).NormalizeVal(tempDouble));
		}
		return ins;
	}

	void read(String fileName) {
		try {
			FileReader fr = new FileReader(fileName);
			BufferedReader br = new BufferedReader(fr);
			String line = br.readLine();
			this.titulo = line;
			// Guarda cada valor de la linea en string separados
			String[] tempArr;
			tempArr = line.split(DELIMETER);
			numAttributes = tempArr.length;
			attributes = new ArrayList<>();
			normalizedAttributes = new ArrayList<>();
			types = new ArrayList<>();
			// Crea los ArrayList seg�n los elementos que haya
			for (int i = 0; i < numAttributes - 1; i++) {
				attributes.add(new Attribute_Numeric());
				normalizedAttributes.add(new Attribute_Numeric());
			}
			attributes.add(new Attribute_Qualitative());
			normalizedAttributes.add(new Attribute_Qualitative());
			// Lee cada linea y almacena cada valor en su respectivo atributo
			while ((line = br.readLine()) != null) {
				tempArr = line.split(DELIMETER);
				for (int i = 0; i < numAttributes; i++) {
					attributes.get(i).Add(tempArr[i]);
					normalizedAttributes.get(i).Add(tempArr[i]);
					// contar los tipos de flores
					if (i == numAttributes - 1)
						addType(tempArr[i]);
				}
			}
			normalize();
			numInstances = attributes.get(0).Size();
			br.close();
			fr.close();
		} catch (Exception e) {
			System.out.println("Error: " + e.getMessage());
		}
	}

	public void write() {
		System.out.println(titulo);
		for (int i = 0; i < numInstances; i++) {
			for (int j = 0; j < numAttributes; j++) {
				attributes.get(j).Write(i);
			}
			System.out.println();
		}
	}

	public void writeNormalized() {
		System.out.println(titulo);
		for (int i = 0; i < numInstances; i++) {
			for (int j = 0; j < numAttributes; j++) {
				normalizedAttributes.get(j).Write(i);
			}
			System.out.println();
		}
	}

	private void addType(String s) {
		int count = 0;
		for (String str : types) {
			if (str.equals(s))
				count++;
		}
		if (count == 0)
			types.add(s);
	}
}
