import { Component } from '@angular/core';

@Component({
  selector: 'app-image-uploader',
  template: `
            <label for="inputAvatar">Avatar</label>
          <input
            type="text"
            class="form-control"
            id="inputAvatar"
            placeholder="Upload avatar"
          />
    <input type="file" accept="image/*" (change)="onFileChange($event)">
    `,
})
export class ImageUploaderComponent {
  imageUrl: string | ArrayBuffer | null = null;

  onFileChange(event: Event): void {
    const target = event.target as HTMLInputElement;
    if (target.files && target.files.length > 0) {
      const reader = new FileReader();
      reader.onload = (e: ProgressEvent<FileReader>) => {
        if (e.target && e.target.result) {
          this.imageUrl = e.target.result as string | ArrayBuffer;
        }
      };
      reader.readAsDataURL(target.files[0]);
    }
  }
}