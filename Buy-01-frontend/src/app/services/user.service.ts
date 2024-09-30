import { Injectable } from "@angular/core";
import { HttpClient } from "@angular/common/http";
import { Observable } from "rxjs";
import { User } from "../models/Users";

@Injectable({
  providedIn: "root",
})
export class UserService {
  private apiUrl = "http://localhost:8081/api/users";

  constructor(private http: HttpClient) {}

  getAllUsers(): Observable<User[]> {
    return this.http.get<User[]>(`${this.apiUrl}`);
  }

  getUserById(id: string): Observable<User> {
    return this.http.get<User>(`${this.apiUrl}/${id}`);
  }
  createUser(user: User): Observable<User> {
    return this.http.post<User>(this.apiUrl + "/register", user);
  }

  loginUser(user: User): Observable<any> {
    return this.http.post<User>(this.apiUrl + "/login", user);
  }

  updateUser(id: string, user: User): Observable<User> {
    return this.http.put<User>(`${this.apiUrl}/${id}`, user);
  }

  deleteUser(id: string): Observable<void> {
    return this.http.delete<void>(`${this.apiUrl}/${id}`);
  }
  sendAvatar(file: FormData): Observable<any> {
    return this.http.post(`${this.apiUrl}/uploadavatar`, file);
  }

  //TODO: implement `sendAvatar` function cette fonction
  //avoir comme parametres un fomr qui contien une seule valeur file apres l'envoyer en mulpaert sur le enpont "uploadavatar"
}
