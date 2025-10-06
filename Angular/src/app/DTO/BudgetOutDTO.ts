export class BudgetOutDTO {
  	messageString: string | undefined;
	private budget: any;
	private errorflag: boolean=false;
    private validationErrors: Map<string, string> | undefined;
	private validationFlag: boolean=false;
}
