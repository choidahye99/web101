import { API_BASE_URL } from "../api-config";
import axios from "axios";

export function call(api, method, request){
    let options = {
        headers: new Headers({
            "Content-Type" : "application/json"
        }),
        url: API_BASE_URL + api,
        method: method,
    };

    if (request) {
        //GET
        options.data = request;
    }

    return axios(options)
    .then((response) => {
        if (response.status === 200) {
            return response.data
        }
    }).catch((error)=> {
        console.log("http error");
        console.log(error)
    })
}