#include <stdio.h>
#include <stdlib.h>
#include <math.h>

double x_r[50] = {
    0.00, 0.10, 0.20, 0.30, 0.40, 0.50, 0.60, 0.70, 0.80, 0.90,
    1.00, 1.10, 1.20, 1.30, 1.40, 1.50, 1.60, 1.70, 1.80, 1.90,
    2.00, 2.10, 2.20, 2.30, 2.40, 2.50, 2.60, 2.70, 2.80, 2.90,
    3.00, 3.10, 3.20, 3.30, 3.40, 3.50, 3.60, 3.70, 3.80, 3.90,
    4.00, 4.10, 4.20, 4.30, 4.40, 4.50, 4.60, 4.70, 4.80, 4.90
};

double y_k[50] = {
    7.0000, 7.4942, 8.1770, 9.0482, 10.1080, 11.3562, 12.7929, 14.4181, 16.2319, 18.2341,
    20.4248, 22.8040, 25.3717, 28.1279, 31.0726, 34.2058, 37.5274, 41.0376, 44.7363, 48.6234,
    52.6991, 56.9633, 61.4159, 66.0571, 70.8867, 75.9049, 81.1115, 86.5066, 92.0903, 97.8624,
    103.8230, 109.9721, 116.3097, 122.8358, 129.5504, 136.4535, 143.5451, 150.8252, 158.2938, 165.9509,
    173.7964, 181.8305, 190.0531, 198.4641, 207.0637, 215.8518, 224.8283, 233.9933, 243.3469, 252.8889
};


// one neuron
double calc_neuron(int neurons, double input_weights[], double input_values[], double bias) {
	int i, j;
	double sum = bias;
	for (i = 0; i < neurons; i++) {
		sum += input_weights[i] * input_values[i];
	}
	return sum;
}

// calc net

#define L1N 4
#define L2N 4

double layer1[L1N][1];
double layer2[L2N][L1N];
double layer3[1][L2N];

double bias1[L1N];
double bias2[L2N];
double bias3[1];

double output0[1];
double output1[L1N];
double output2[L2N];

int main(int argc, char* argv[]) {
    double *y_r = y_k;

    int total_expected_args = 1 + L1N + L1N * L2N + L2N + L1N + L2N + 1;
    if (argc != total_expected_args) {
        // broj argumenata nije odgovarajuci
        fprintf(stderr, "Broj argumenata nije odgovarajuci, ocekivano %d realnih vrednosti.\n", total_expected_args - 1);
        exit(1);
    }

    // parsiranje
    int i, j, k, ai = 1;
    // layer1
    for (i = 0; i < L1N; i++, ai++) {
        layer1[i][0] = atof(argv[ai]);
    }
    // layer2
    for (i = 0; i < L2N; i++) {
        for (j = 0; j < L1N; j++, ai++) {
            layer2[i][j] = atof(argv[ai]);
        }
    }
    // layer3
    for (i = 0; i < L2N; i++) {
        layer3[0][i] = atof(argv[ai]);
    }
    // bias1
    for (i = 0; i < L1N; i++, ai++) {
        bias1[i] = atof(argv[ai]);
    }
    // bias2
    for (i = 0; i < L2N; i++, ai++) {
        bias2[i] = atof(argv[ai]);
    }
    // bias3
    bias3[0] = atof(argv[ai]);

    // idemo dalje!
    double mse = 0;

    for (k = 0; k < 50; k++) {
        output0[0] = x_r[k];
        // layer1
        for (i = 0; i < L1N; i++) {
            output1[i] = calc_neuron(1, layer1[i], output0, bias1[i]);
        }
        // layer2
        for (i = 0; i < L2N; i++) {
            output2[i] = calc_neuron(L1N, layer2[i], output1, bias2[i]);
        }
        // layer3
        double val = calc_neuron(L2N, layer3[0], output2, bias3[0]);

        double err = pow(y_r[k] - val, 2);
        mse += err;
    }

    FILE *f=fopen("trosak.txt","w");
    fprintf(f,"%.15f\n", mse / 50);
    fclose(f);
    return 0;
}