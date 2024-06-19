const path = require('path');

module.exports = {
    entry: './public/typescript/index.ts',
    module: {
        rules: [
            {
                test: /\.tsx?$/,
                use: 'ts-loader',
                exclude: /node_modules/,
            },
        ],
    },
    resolve: {
        extensions: ['.tsx', '.ts', '.js'],
    },
    output: {
        filename: 'typescript_bundle.js',
        path: path.resolve(__dirname, 'public', 'typescript_dist'),
    },
};
module.exports = {
    entry: './public/typescript/admin.ts',
    module: {
        rules: [
            {
                test: /\.tsx?$/,
                use: 'ts-loader',
                exclude: /node_modules/,
            },
        ],
    },
    resolve: {
        extensions: ['.tsx', '.ts', '.js'],
    },
    output: {
        filename: 'typescript_admin_bundle.js',
        path: path.resolve(__dirname, 'public', 'typescript_dist'),
    },
};