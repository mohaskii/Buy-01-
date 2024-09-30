    import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Media } from '../models/Medias';

@Injectable({
  providedIn: 'root'
})
export class MediaService {

  private apiUrl = 'http://localhost:8083/api/media';

  constructor(private http: HttpClient) { }

  getAllMedia(): Observable<Media[]> {
    return this.http.get<Media[]>(`${this.apiUrl}`);
  }
     
  getMediaById(id: string): Observable<Media> {
    return this.http.get<Media>(`${this.apiUrl}/${id}`);
  }

  uploadMedia(file: File, productId: string): Observable<Media> {
    const formData = new FormData();    
    formData.append('file', file)   ;
    formData.append('productId', productId);
    return this.http.post<Media>(this.apiUrl, formData);
  }

  updateMedia(id: string, media: Media): Observable<Media> {
    return this.http.put<Media>(`${this.apiUrl}/${id}`, media);
  }

  deleteMedia(id: string): Observable<void> {
    return this.http.delete<void>(`${this.apiUrl}/${id}`);
  }

}