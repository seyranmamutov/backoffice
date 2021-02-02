import {basePath} from './config';

export function buildUrl(relativeUrl) {
    if (basePath.endsWith('/')) {
        return basePath + relativeUrl
    } else {
        return basePath + '/' + relativeUrl;
    }
}
