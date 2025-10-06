
export class ResponseOutDTO {
  messageString: string = '';
  employee: any;
  errorflag: boolean = false;
  validationErrors: { [key: string]: string } = {};
  validationFlag!: boolean;;
}